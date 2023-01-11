package com.solver.model;

import java.util.ArrayList;

public class UE {

	/*
	 * Une UE possède 3 variables d'instance : un nom, différents modules regroupés
	 * dans une liste, un nombre de modules.
	 */
	private String name;
	private ArrayList<Module> liste_modules;
	private int nb_modules;

	public UE(String name, ArrayList<Module> liste_modules, int nb_modules) {
		this.name = name;
		this.liste_modules = liste_modules;
		this.nb_modules = nb_modules;
	}

	public UE(String name, ArrayList<Module> liste_modules) {
		this.name = name;
		this.liste_modules = liste_modules;
	}

	public UE(String name, int nb_modules) {
		this.name = name;
		this.nb_modules = nb_modules;
		this.liste_modules = new ArrayList<Module>(nb_modules);
	}

	public int getNb_cours_different() {
		return liste_modules.size();
	}

	public int getNb_cours() {
		int Result = 0;
		for (Module i : liste_modules) {
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

	public ArrayList<Module> getListeModules() {
		return liste_modules;
	}

	public void setListeModules(ArrayList<Module> ue) {
		liste_modules = ue;
	}

	public int getNb_modules() {
		return nb_modules;
	}

	public void setNb_modules(int nb_modules) {
		this.nb_modules = nb_modules;
	}

	public void add(Module m) {
		this.add(m);
	}

}
