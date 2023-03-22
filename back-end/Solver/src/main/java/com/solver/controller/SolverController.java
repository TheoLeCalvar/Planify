package com.solver.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solver.model.Request;
import com.solver.service.SolverService;

@CrossOrigin
@RestController
@RequestMapping("/solver")
public class SolverController {

	@Autowired
	private SolverService service;

	@PostMapping(path = "", produces = "text/plain")
	public ResponseEntity<String> solver(@RequestBody Request request) {
		try {
			return new ResponseEntity<String>(service.solver(request), HttpStatus.OK);
		} catch (IOException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
