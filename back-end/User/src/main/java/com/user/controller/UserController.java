package com.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.model.User;
import com.user.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService service;

	@GetMapping(path = "/list", produces = "application/json")
	public ResponseEntity<List<User>> listUsers() {
		return new ResponseEntity<List<User>>(service.listAll(), HttpStatus.OK);
	}

	@GetMapping(path = "/{mail}", produces = "application/json")
	public ResponseEntity<User> getUser(@PathVariable("mail") String mail) {
		try {
			return new ResponseEntity<User>(service.get(mail), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<User>(new User(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(path = "/new", produces = "application/json")
	public void saveUser(@RequestBody User user) {
		service.save(user);
	}

}
