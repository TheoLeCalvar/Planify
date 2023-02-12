package com.data.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.data.model.DataCalendar;

public interface DataCalendarRepo extends MongoRepository<DataCalendar, String> {

	DataCalendar findTopByOrderByCreationDateDesc();
	
}
