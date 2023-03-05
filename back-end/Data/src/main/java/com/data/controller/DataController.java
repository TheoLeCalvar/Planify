package com.data.controller;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.data.model.DataCalendar;
import com.data.model.User;
import com.data.service.DataService;

import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/data")
public class DataController {

	@Autowired
	private DataService service;

	@GetMapping(path = "/list", produces = "application/json")
	public ResponseEntity<String> listCalendars() {
		return new ResponseEntity<String>(service.listAll(), HttpStatus.OK);
	}

	@GetMapping(path = "/{id}", produces = "application/csv")
	public ResponseEntity<Resource> getCalendar(@PathVariable("id") String id) {
		String fileName = id + ".csv";
		try {
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
					.body(service.getFileCalendar(fileName));
		} catch (FileNotFoundException e) {
			System.out.println(e);
			return ResponseEntity.badRequest().body(null);
		}
	}

	@DeleteMapping(path = "/{id}")
	public void deleteCalendar(@PathVariable("id") String id) {
		service.delete(id);
	}

	@PostMapping(path = "/save-data-calendar", produces = "application/json")
	public void saveDataCalendar(@Valid @RequestBody DataCalendar data) {
		service.saveDataCalendar(data);
	}

	@PostMapping(path = "/save-preferences", produces = "application/json")
	public void savePreferences(@RequestBody User user) {
		service.savePreferences(user);
	}

	@PostMapping(path = "/solver", produces = "application/json")
	public ResponseEntity<String> solver() {
		try {
			return new ResponseEntity<String>("{\"fileName\":\"" + service.solver(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("{\"error\":\"" + e.getMessage() + "\"}", HttpStatus.BAD_REQUEST);
		}
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException e) {
		Map<String, String> errors = new HashMap<>();
		e.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}

}
