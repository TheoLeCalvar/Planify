package com.solver.model;

public class Module {

	private int numero_module;
	private String name;
	private int nb_creneaux;
	
	public Module(int numero_module, String name, int nb_creneaux) {
		super();
		this.numero_module = numero_module;
		this.name = name;
		this.nb_creneaux = nb_creneaux;
	}

	public int getNumero_module() {
		return numero_module;
	}

	public String getName() {
		return name;
	}

	public int getNb_creneaux() {
		return nb_creneaux;
	}

}
