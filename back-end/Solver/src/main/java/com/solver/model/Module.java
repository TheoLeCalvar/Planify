package com.solver.model;

public class Module {

	private int Nb_Creneaux;
	private String name; // TODO: Est-ce que c'est nécessaire ?
	private int Numero_module;

	public Module(int n, String name, int w) {
		this.Nb_Creneaux=n;
		this.name=name;
		this.Numero_module=w;
	}

	public int getNb_Creneaux() {
		return Nb_Creneaux;
	}

	public int getNumero_module() {
		return Numero_module;
	}

	public void setNumero_module(int numero_module) {
		Numero_module = numero_module;
	}

	public void setNb_Creneaux(int nb_Creneaux) {
		Nb_Creneaux = nb_Creneaux;
	}

}
