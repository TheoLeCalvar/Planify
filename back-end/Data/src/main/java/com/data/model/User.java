package com.data.model;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;

public class User {

	@Id
	private String mail;
	private String rol;
	private ArrayList<Unavailable> unavailables;
	private String localisation;
	
}
