package com.solver.model;

import java.util.ArrayList;

import com.solver.util.Localisation;
import com.solver.util.Role;

public class User {

	private String mail;
	private Role role;
	private ArrayList<Unavailability> unavailabilities;
	private Localisation localisation;
	private ArrayList<Integer> availabilities;
	private int Etalement_semaines; // ??

	public User(String mail, Role role, ArrayList<Unavailability> unavailables, Localisation localisation,
			int etalement_semaines) {
		super();
		this.mail = mail;
		this.role = role;
		this.unavailabilities = unavailables;
		this.localisation = localisation;
		Etalement_semaines = etalement_semaines;
	}

	public String getMail() {
		return mail;
	}

	public Role getRole() {
		return role;
	}

	public ArrayList<Unavailability> getUnavailables() {
		return unavailabilities;
	}

	public Localisation getLocalisation() {
		return localisation;
	}

	public ArrayList<Integer> getAvailabilities() {
		return availabilities;
	}

	public int getEtalement_semaines() {
		return Etalement_semaines;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void setUnavailables(ArrayList<Unavailability> unavailables) {
		this.unavailabilities = unavailables;
	}

	public void setLocalisation(Localisation localisation) {
		this.localisation = localisation;
	}

	public void setAvailabilities(ArrayList<Integer> availabilities) {
		this.availabilities = availabilities;
	}

	public void setEtalement_semaines(int etalement_semaines) {
		Etalement_semaines = etalement_semaines;
	}

}
