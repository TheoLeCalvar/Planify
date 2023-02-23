package com.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

	public List<DataCalendar> listAll() {
		return dataCalendarRepo.findAll();
	}

	public DataCalendar get(String id) {
		return dataCalendarRepo.findById(id).get();
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
		User userDB = restTemplate.getForEntity(Constants.getUrlUser() + "/" + user.getMail(), User.class)
				.getBody();
		userDB.setUnavailabilities(user.getUnavailabilities());
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

		String calendar = restTemplate.postForEntity(Constants.getUrlSolver(), dataCalendar, String.class).getBody();

		dataCalendar.setCalendar(calendar);
		save(dataCalendar);
		return calendar;
	}

}
