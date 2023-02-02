package com.solver.model;

import java.util.ArrayList;

public class Module {

	private int numero_module;
	private String name;
	private int nb_creneaux;
	private ArrayList<Integer> Dispo;
	
	public Module(int numero_module, String name, int nb_creneaux, ArrayList<Integer> dispo) {
		super();
		this.numero_module = numero_module;
		this.name = name;
		this.nb_creneaux = nb_creneaux;
		this.Dispo = dispo;
	}

	public int getNumero_module() {
		return numero_module;
	}
	public ArrayList<Integer> getDispo() {
		return Dispo;
	}

	public String getName() {
		return name;
	}
	
	
	public int getNb_creneaux() {
		return nb_creneaux;
	}
	public boolean Creneaux_dispo(int i) { // Prend en entrée un créneaux et vérifie si il est dispo==true ou non==false
		if (this.getDispo().get(i) == 1) {
			return true;
		} else {
			return false;
		}
	}

}
