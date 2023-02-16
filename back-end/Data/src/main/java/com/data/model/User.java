package com.data.model;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;

import com.data.util.Localisation;
import com.data.util.Role;

public class User {

	@Id
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

	public ArrayList<Unavailability> getunavailabilities() {
		return unavailabilities;
	}

	public Localisation getLocalisation() {
		return localisation;
	}

	public void setunavailabilities(ArrayList<Unavailability> unavailabilities) {
		this.unavailabilities = unavailabilities;
	}

}
