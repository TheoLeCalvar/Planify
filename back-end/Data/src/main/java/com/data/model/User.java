package com.data.model;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;

import com.data.util.Localisation;
import com.data.util.Role;

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

	public void setUnavailables(ArrayList<Unavailable> unavailables) {
		this.unavailables = unavailables;
	}

}
