package com.user.model;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;

import com.user.util.Localisation;
import com.user.util.Role;

public class User {

	@Id
	private String mail;
	private Role role;
	private ArrayList<Unavailable> unavailables;
	private Localisation localisation;

	public String getMail() {
		return mail;
	}

	public Role getRole() {
		return role;
	}

	public ArrayList<Unavailable> getUnavailables() {
		return unavailables;
	}

	public Localisation getLocalisation() {
		return localisation;
	}

}
