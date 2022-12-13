package com.solver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solver.service.SolverService;

@CrossOrigin
@RestController
@RequestMapping("/solver")
public class SolverController {

	@Autowired
	private SolverService service;
	
	@GetMapping(path = "", produces = "application/json")
	public ResponseEntity<String> listPhotos() {
		service.solver();
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}
	
}
