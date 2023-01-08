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
		
	public List<Data> listAll() {
		return repo.findAll();
	}
	
	public Data get(String id) {
		try {
			return repo.findById(id).get();
		}
		catch (Exception e) { 
			// TODO: handling error
			return new Data();
		}
	}
	
	public void delete(String id) {
		repo.deleteById(id);
	}

	public String solver(Data data) {
		String requestBody = new Gson().toJson(data);
		requestBody = requestBody.replaceAll("id", "Numero_module");
		requestBody = requestBody.replaceAll("slotsNumber", "Nb_Creneaux");
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(requestBody, headers);
		return restTemplate.postForEntity(URL_SOLVER, request, String.class).getBody();
	}
	
}
