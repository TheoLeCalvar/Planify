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

import com.data.model.DataCalendar;
import com.data.service.DataService;

@CrossOrigin
@RestController
@RequestMapping("/data")
public class DataController {

	@Autowired
	private DataService service;

	@GetMapping(path = "/list", produces = "application/json")
	public ResponseEntity<List<DataCalendar>> listSolutions() {
		return new ResponseEntity<List<DataCalendar>>(service.listAll(), HttpStatus.OK);
	}

	@GetMapping(path = "/{id}", produces = "application/json")
	public ResponseEntity<DataCalendar> getSolution(@PathVariable("id") String id) {
		try {
			return new ResponseEntity<DataCalendar>(service.get(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<DataCalendar>(new DataCalendar(), HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping(path = "/{id}")
	public void deleteSolution(@PathVariable("id") String id) {
		service.delete(id);
	}

	@PostMapping(path = "/solver", produces = "application/json")
	public ResponseEntity<String> solver(@RequestBody DataCalendar data) {
		return new ResponseEntity<String>("{\"reponse\":\"" + service.solver(data).replaceAll("\n", "   ") + "\"}",
				HttpStatus.OK);
	}

}
