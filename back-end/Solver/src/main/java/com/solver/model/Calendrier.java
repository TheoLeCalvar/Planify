package com.solver.model;

import java.util.ArrayList;

public class Calendrier {

	private int Nb_Semaines;
	// Liste du nombre de créneaux contenant 1 si il sagit d'un créneaux dispo et 0 sinon
	private ArrayList<Integer> Dispo;

	// COnstructeur prenant en entrée: le nombre n de semaines et une liste d'indispo
	public Calendrier(int n, ArrayList<Integer> list) {
		this.Dispo = list;
		this.Nb_Semaines = n;
	}

	// Constructeur prenant que le nombre de semaine ( Cas où il n' y a pas d'indispo)
	public Calendrier(int n) {
		this.Dispo = new ArrayList<Integer>(n * 6 * 2);
		for (int i = 0; i < n * 6 * 2; i++) {
			this.Dispo.add(1);
		}
		this.Nb_Semaines = n;
	}

	public int getNb_Semaines() {
		return Nb_Semaines;
	}

	public ArrayList<Integer> getDispo() {
		return Dispo;
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

	// Prend en entrée un créneaux et vérifie si il est dispo==true ou non==false
	public boolean Creneaux_dispo(int i) {
		if (this.getDispo().get(i) == 1) {
			return true;
		} else {
			return false;
		}
	}

}
