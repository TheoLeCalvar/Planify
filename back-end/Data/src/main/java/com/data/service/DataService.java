package com.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.data.model.Data;
import com.data.repo.DataRepo;

@Service
public class DataService {

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
	
}
