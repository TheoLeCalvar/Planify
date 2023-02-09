package com.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.data.model.DataCalendar;
import com.data.repo.DataCalendarRepo;
import com.data.util.Constants;
import com.google.gson.Gson;

@Service
public class DataService {

	@Autowired
	protected RestTemplate restTemplate;

	@Autowired
	private DataCalendarRepo dataCalendarRepo;

	public void save(DataCalendar data) {
		dataCalendarRepo.save(data);
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

	public String solver(DataCalendar data) {
		String requestBody = new Gson().toJson(data);
		requestBody = requestBody.replaceAll("slotsNumber", "nb_creneaux");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(requestBody, headers);
		String calendar = restTemplate.postForEntity(Constants.getUrlSolver(), request, String.class).getBody();

//		TODO: Notifier tous les enseinants
		
		data.setCalendar(calendar);
		save(data);
		return calendar;
	}

}
