package com.data.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.data.model.Data;

public interface DataRepo extends MongoRepository<Data, String> {
		
}
