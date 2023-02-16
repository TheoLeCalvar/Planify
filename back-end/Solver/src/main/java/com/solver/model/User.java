package com.solver.model;

import java.util.ArrayList;

public class User {
	private String mail;
	private String role;
	private ArrayList<Integer> Dispo;
	private String localisation;
	private int Etalement_semaines;
	
	
	public User(String mail, String role, ArrayList<Integer> dispo, String localisation, int etalement_semaines) {
		super();
		this.mail = mail;
		this.role = role;
		Dispo = dispo;
		this.localisation = localisation;
		Etalement_semaines = etalement_semaines;
	}


	public String getMail() {
		return mail;
	}


	public void setMail(String mail) {
		this.mail = mail;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	public ArrayList<Integer> getDispo() {
		return Dispo;
	}


	public void setDispo(ArrayList<Integer> dispo) {
		Dispo = dispo;
	}


	public String getLocalisation() {
		return localisation;
	}


	public void setLocalisation(String localisation) {
		this.localisation = localisation;
	}


	public int getEtalement_semaines() {
		return Etalement_semaines;
	}


	public void setEtalement_semaines(int etalement_semaines) {
		Etalement_semaines = etalement_semaines;
	}
	
	
	
	
	
	
}


