package com.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.model.User;
import com.user.repo.UserRepo;

@Service
public class UserService {

	@Autowired
	private UserRepo userRepo;

	public void save(User user) {
		userRepo.save(user);
	}

	public List<User> listAll() {
		return userRepo.findAll();
	}

	public User get(String mail) {
		return userRepo.findByMail(mail);
	}

}
