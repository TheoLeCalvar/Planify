package com.solver.model;

import java.util.ArrayList;

public class Module {

	private int numero_module;
	private String name;
	private int nb_creneaux;
	private User UserN;
	private User UserB;
	private boolean Sync; // 0 si le créneau n'as pas besoin d'etre sync avec brest, 1 si oui

	
	public Module(int numero_module, String name, int nb_creneaux,User userN, User userB, boolean sync) {
		super();
		this.numero_module = numero_module;
		this.name = name;
		this.nb_creneaux = nb_creneaux;
		this.Sync = sync;
		this.UserB=userB;
		this.UserN=userN;
		
	}

	public User getUserN() {
		return UserN;
	}


	public void setUserN(User userN) {
		UserN = userN;
	}



	public User getUserB() {
		return UserB;
	}



	public void setUserB(User userB) {
		UserB = userB;
	}



	public int getEtalement_semainesB() {
		return this.getUserB().getEtalement_semaines();
	}
	public int getEtalement_semainesN() {
		return this.getUserN().getEtalement_semaines();
	}

	public boolean isSync() {
		return Sync;
	}


	public void setSync(boolean sync) {
		Sync = sync;
	}


	public int getNumero_module() {
		return numero_module;
	}
	public ArrayList<Integer> getDispoN() {
		return this.getUserN().getDispo();
	}
	public ArrayList<Integer> getDispoB() {
		return this.getUserB().getDispo();
	}

	public String getName() {
		return name;
	}
	
	public int getNb_creneaux() {
		return nb_creneaux;
	}
	public boolean Creneaux_dispoN(int i) { // Prend en entrée un créneaux et vérifie si il est dispo==true ou non==false
		if (this.getDispoN().get(i) == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean Creneaux_dispoB(int i) { // Prend en entrée un créneaux et vérifie si il est dispo==true ou non==false
		if (this.getDispoB().get(i) == 1) {
			return true;
		} else {
			return false;
		}
	}

}
