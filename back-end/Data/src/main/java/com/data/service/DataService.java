package com.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.data.model.Data;
import com.data.repo.DataRepo;
import com.google.gson.Gson;

@Service
public class DataService {

	final private String URL_SOLVER = "http://solver:3201/solver";

	@Autowired
	protected RestTemplate restTemplate;

	@Autowired
	private DataRepo repo;

	public void save(Data data) {
		repo.save(data);
	}

	public List<Data> listAll() {
		return repo.findAll();
	}

	public Data get(String id) {
		return repo.findById(id).get();
	}

	public void delete(String id) {
		repo.deleteById(id);
	}

	public String solver(Data data) {
		String requestBody = new Gson().toJson(data);
		requestBody = requestBody.replaceAll("id", "numero_module");
		requestBody = requestBody.replaceAll("slotsNumber", "nb_creneaux");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(requestBody, headers);
		String calendar = restTemplate.postForEntity(URL_SOLVER, request, String.class).getBody();
		
		data.setCalendar(calendar);
		save(data);
		return calendar;
	}

}
