package com.data.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.data.model.DataCalendar;
import com.data.model.User;
import com.data.repo.DataCalendarRepo;
import com.data.util.Constants;

@Service
public class DataService {

	@Autowired
	protected RestTemplate restTemplate;

	@Autowired
	private DataCalendarRepo dataCalendarRepo;

	public void save(DataCalendar dataCalendar) {
		dataCalendarRepo.save(dataCalendar);
	}

	public String listAll() {
		List<DataCalendar> list = dataCalendarRepo.findAll();
		ArrayList<String> res = new ArrayList<>();
		for (DataCalendar dataCalendar : list) {
			String teacherWaitingList = dataCalendar.getTeacherWaitingList().stream().map(Object::toString)
					.collect(Collectors.joining("\",\""));
			res.add("{\"id\":\"" + dataCalendar.getId() + "\",\"creationDate\":" + dataCalendar.getCreationDate()
					+ ",\"existCalendarFile\":" + dataCalendar.existCalendarFile() + ",\"teacherWaitingList\":["
					+ (teacherWaitingList.length() > 0 ? "\"" + teacherWaitingList + "\"" : "") + "]}");
		}
		return res.toString();
	}

	public DataCalendar get(String id) {
		return dataCalendarRepo.findById(id).get();
	}

	public InputStreamResource getCalendarFile(String fileName) throws FileNotFoundException {
		File file = new File("/var/lib/data/files/" + fileName);
		return new InputStreamResource(new FileInputStream(file));
	}

	public void delete(String id) {
		dataCalendarRepo.deleteById(id);
	}

	public void saveDataCalendar(DataCalendar dataCalendar) {
		dataCalendar.setCreationDate(System.currentTimeMillis());
		dataCalendar.generateTeacherWaitingList();
		save(dataCalendar);
//		TODO: Notify all teachers by e-mail
	}

	public void savePreferences(User user) {
		if (user.getMail().isBlank()) {
			throw new Error("mail is mandatory");
		}
		User userDB = restTemplate.getForEntity(Constants.getUrlUser() + "/" + user.getMail(), User.class).getBody();
		userDB.setUnavailabilities(user.getUnavailabilities());
		userDB.setSpreadWeeks(user.getSpreadWeeks());
		restTemplate.postForEntity(Constants.getUrlUser() + "/save", userDB, User.class).getBody();

		DataCalendar dataCalendar = dataCalendarRepo.findTopByOrderByCreationDateDesc();
		dataCalendar.deleteMailToList(user.getMail());
		save(dataCalendar);
	}

	public String solver() {
		DataCalendar dataCalendar = dataCalendarRepo.findTopByOrderByCreationDateDesc();
		if (dataCalendar.getTeacherWaitingList().size() != 0) {
			throw new Error("teacherWaitingList is not empty");
		}

		String fileName = restTemplate.postForEntity(Constants.getUrlSolver(), dataCalendar, String.class).getBody();

		dataCalendar.setExistCalendarFile(true);
		save(dataCalendar);
		return fileName;
	}

}
