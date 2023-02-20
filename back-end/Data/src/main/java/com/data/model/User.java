package com.data.model;

import java.util.ArrayList;

import com.data.util.Localisation;
import com.data.util.Role;

public class User {

	private String mail;
	private Role role;
	private ArrayList<Unavailability> unavailabilities;
	private Localisation localisation;

	public String getMail() {
		return mail;
	}

	public Role getRole() {
		return role;
	}

	public ArrayList<Unavailability> getUnavailabilities() {
		return unavailabilities;
	}

	public Localisation getLocalisation() {
		return localisation;
	}

	public void setUnavailabilities(ArrayList<Unavailability> unavailabilities) {
		this.unavailabilities = unavailabilities;
	}

}
