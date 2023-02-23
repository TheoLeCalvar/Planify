package com.solver.model;

import java.util.ArrayList;

import com.solver.util.Localisation;
import com.solver.util.Role;

public class User {

	private String mail;
	private Role role;
	private ArrayList<Unavailability> unavailabilities;
	private Localisation localisation;
	private ArrayList<Integer> unavailabilitiesTraduction;
	private int spreadWeeks;

	public User() {
		super();
	}

	public User(String mail, Role role, ArrayList<Unavailability> unavailables, Localisation localisation,
			int etalement_semaines) {
		this.mail = mail;
		this.role = role;
		this.unavailabilities = unavailables;
		this.localisation = localisation;
		this.spreadWeeks = etalement_semaines;
	}

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

	public ArrayList<Integer> getUnavailabilitiesTraduction() {
		return unavailabilitiesTraduction;
	}

	public int getEtalement_semaines() {
		return spreadWeeks;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void setUnavailabilities(ArrayList<Unavailability> unavailables) {
		this.unavailabilities = unavailables;
	}

	public void setLocalisation(Localisation localisation) {
		this.localisation = localisation;
	}

	public void setUnavailabilitiesTraduction(ArrayList<Integer> unavailabilitiesTraduction) {
		this.unavailabilitiesTraduction = unavailabilitiesTraduction;
	}

	public void setEtalement_semaines(int etalement_semaines) {
		this.spreadWeeks = etalement_semaines;
	}

}
