package com.solver.model;

public class Module {

	private int nb_creneaux;
	private String name;
	private int numero_module;

	public Module(int n, String name, int numero_module) {
		this.nb_creneaux = n;
		this.name = name;
		this.numero_module = numero_module;
	}

	public int getNb_Creneaux() {
		return nb_creneaux;
	}

	public int getNumero_module() {
		return numero_module;
	}

	public void setNumero_module(int numero_module) {
		this.numero_module = numero_module;
	}

	public void setNb_Creneaux(int nb_creneaux) {
		this.nb_creneaux = nb_creneaux;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
