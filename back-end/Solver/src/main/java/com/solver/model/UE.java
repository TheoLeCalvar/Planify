package com.solver.model;

import java.util.ArrayList;

public class UE {

	private String name;
	private ArrayList<Module> Ue;
	private int Nb_module;

	public UE(String name, ArrayList<Module> Ue, int Nb_module) {
		this.name = name;
		this.Ue = Ue;
		this.Nb_module = Nb_module;
	}

	public UE(String name, ArrayList<Module> Ue) {
		this.name = name;
		this.Ue = Ue;
	}

	public UE(String name, int Nb_module) {
		this.name = name;
		this.Nb_module = Nb_module;
		this.Ue = new ArrayList<Module>(Nb_module);
	}

	public int getNb_cours_different() {
		return Ue.size();
	}

	public int getNb_cours() {
		int Result = 0;
		for (Module i : Ue) {
			Result = Result + i.getNb_Creneaux();
		}
		return Result;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Module> getUe() {
		return Ue;
	}

	public void setUe(ArrayList<Module> ue) {
		Ue = ue;
	}

	public int getNb_module() {
		return Nb_module;
	}

	public void setNb_module(int nb_module) {
		Nb_module = nb_module;
	}

	public void add(Module m) {
		this.add(m);
	}

}
