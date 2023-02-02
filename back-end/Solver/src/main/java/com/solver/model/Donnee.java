package com.solver.model;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;

public class Donnee {

	private ArrayList<UE> Liste_UE;
	private Calendrier calendrier;

	public Donnee(ArrayList<Module> UE_A, ArrayList<Module> UE_B, ArrayList<Module> UE_C, int Nb_Semaines,
			ArrayList<Integer> Dispo) {
		this.Liste_UE = new ArrayList<UE>(3);
		UE uea = new UE("UE_A", UE_A);
		UE ueb = new UE("UE_B", UE_B);
		UE uec = new UE("UE_C", UE_C);
		this.Liste_UE.add(uea);
		this.Liste_UE.add(ueb);
		this.Liste_UE.add(uec);
		this.calendrier = new Calendrier(Nb_Semaines, Dispo);
	}

	public ArrayList<UE> getListe_UE() {
		return Liste_UE;
	}
	
	public ArrayList<Module> getListe_Module(){
		ArrayList<Module> Liste_modules = new ArrayList<Module>();
		for (UE ue:this.Liste_UE) {
			for (Module module:ue.getListeModules()) {
				Liste_modules.add(module);
			}
		}
		return Liste_modules;
	}

	public void setListe_UE(ArrayList<UE> liste_UE) {
		Liste_UE = liste_UE;
	}

	public int Nb_cour_different() {
		int Result = 0;
		for (UE i : Liste_UE) {
			Result = Result + i.getNb_cours_different();
		}
		return Result + 1;
	}

	public int Nb_cours() {
		int Result = 0;
		for (UE i : Liste_UE) {
			Result = Result + i.getNb_cours();
		}
		return Result;
	}

	public int Nb_0() {
		return getCalendrier().getNb_Creneaux() - Nb_cours();
	}

	public int Nb_0Jour() {
		return Nb_0() / getCalendrier().getNb_Jours();
	}

	public Calendrier getCalendrier() {
		return calendrier;
	}

	public void setCalendrier(Calendrier calendrier) {
		this.calendrier = calendrier;
	}

	public ArrayList<Integer> Traduction(ArrayList<LocalDate> date_indispo, ArrayList<Integer> creneau_indispo) {
		LocalDate debut = LocalDate.of(2022, 11, 8);
		ArrayList<Integer> l_int = new ArrayList<Integer>();
		ArrayList<Integer> l_final = new ArrayList<Integer>();
		for (LocalDate date : date_indispo) {
			Duration dure = Duration.between(debut.atTime(0, 0), date.atTime(0, 0));

			System.out.println(dure);
			Integer i = (int) (long) dure.toDays();
			l_int.add((i / 7) * 2 + (i % 7) + 1);
		}
		for (int i : l_int) {
			for (Integer j : creneau_indispo) {
				l_final.add(i * 6 + j);
			}
		}
		ArrayList<Integer> l_creneau = new ArrayList<Integer>();
		// changer 120 avec le nombre de creneaux total ( utiliser class creneaux )
		for (int i = 0; i < 120; i++) {
			for (int e : l_final) {
				if (i == e) {
					l_creneau.add(0);
				} else {
					l_creneau.add(1);
				}
			}
		}
		return l_creneau;
	}

}
