package com.data.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.data.model.Data;
import com.data.service.DataService;

@CrossOrigin
@RestController
@RequestMapping("/data")
public class DataController {

	@Autowired
	private DataService service;
	
	@GetMapping(path = "/list", produces = "application/json")
	public ResponseEntity<List<Data>> listSolutions() {
		return new ResponseEntity<List<Data>>(service.listAll(), HttpStatus.OK);
	}
	
	@GetMapping(path = "/{id}", produces = "application/json")
	public ResponseEntity<Data> getSolution(@PathVariable("id") String id) {
		return new ResponseEntity<Data>(service.get(id), HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/{id}")
	public void deleteSolution(@PathVariable("id") String id) {
		service.delete(id);
	}
	
	@PostMapping(path = "/solver", produces = "application/json")
	public ResponseEntity<String> solver(@RequestBody Data data) {
		return new ResponseEntity<String>("{\"reponse\":\"" + service.solver(data).replaceAll("\n", "   ") + "\"}", HttpStatus.OK);
	}
	
}
