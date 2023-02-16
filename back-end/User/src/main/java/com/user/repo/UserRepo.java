package com.user.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.user.model.User;

public interface UserRepo extends MongoRepository<User, String> {

	User findByMail(String mail);

}
