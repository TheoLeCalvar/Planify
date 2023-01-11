package com.solver.model;

import java.util.ArrayList;

public class Calendrier {

	private int Nb_Semaines;
	private ArrayList<Integer> Dispo; // Liste du nombre de créneaux contenant 1 si il sagit d'un créneaux dispo et 0 sinon

	public Calendrier(int n, ArrayList<Integer> list) { // COnstructeur prenant en entrée: le nombre n de semaines et une liste d'indispo
		this.Dispo = list;
		this.Nb_Semaines = n;
	}

	public Calendrier(int n) {
		this.Dispo = new ArrayList<Integer>(n * 6 * 2); // Constructeur prenant que le nombre de semaine ( Cas où il n' y a pas d'indispo)
		for (int i = 0; i < n * 6 * 2; i++) {
			this.Dispo.add(1);
		}
		this.Nb_Semaines = n;
	}

	public int getNb_Semaines() {
		return Nb_Semaines;
	}

	public void setNb_Semaines(int nb_Semaines) {
		Nb_Semaines = nb_Semaines;
	}

	public ArrayList<Integer> getDispo() {
		return Dispo;
	}

	public void setIndispo(ArrayList<Integer> dispo) {
		Dispo = dispo;
	}

	public int getNb_Creneaux() {
		return this.getNb_Semaines() * 2 * 6;
	}

	public int getNb_CreneauxDispo() {
		int Result = 0;
		for (int i : Dispo) {
			if (i == 1) {
				Result = Result + 1;
			}
		}
		return Result;
	}

	public int getNb_Jours() {
		return this.Nb_Semaines * 2;
	}

	public boolean Creneaux_dispo(int i) { // Prend en entrée un créneaux et vérifie si il est dispo==true ou non==false
		if (this.getDispo().get(i) == 1) {
			return true;
		} else {
			return false;
		}
	}

}
