package com.solver.model;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;

public class Donnee {

	private ArrayList<Module> liste_modulesA;
	private ArrayList<Module> liste_modulesB;
	private ArrayList<Module> liste_modulesC;
	private ArrayList<Module> liste_modules;
	private Calendrier calendrier; // Calendrier général
	private Calendrier calendrierNantes;
	private Calendrier calendrierBrest;
	private String debut;

	public Donnee(ArrayList<Module> UE_A, ArrayList<Module> UE_B, ArrayList<Module> UE_C, int Nb_Semaines,
			ArrayList<Unavailability> unavailabilitiesN, ArrayList<Unavailability> unavailabilitiesB, String debut) {
		this.debut = debut;
		this.calendrier = new Calendrier(Nb_Semaines);
		this.calendrierNantes = new Calendrier(Nb_Semaines, Traduction(unavailabilitiesN));
		this.calendrierBrest = new Calendrier(Nb_Semaines, Traduction(unavailabilitiesB));
		this.liste_modulesA = UE_A;
		this.liste_modulesB = UE_B;
		this.liste_modulesC = UE_C;
		this.liste_modules = new ArrayList<Module>();
		for (Module i : liste_modulesA) {
			liste_modules.add(i);
		}
		for (Module i : liste_modulesB) {
			liste_modules.add(i);
		}
		for (Module i : liste_modulesC) {
			liste_modules.add(i);
		}
	}

	public int Nb_cour_different() {
		return liste_modulesA.size() + liste_modulesB.size() + liste_modulesC.size() + 1;
	}

	public int Nb_cours() {
		int Result = 0;

		for (Module i : liste_modulesA) {
			Result = Result + i.getSlotsNumber();
		}
		for (Module i : liste_modulesB) {
			Result = Result + i.getSlotsNumber();
		}
		for (Module i : liste_modulesC) {
			Result = Result + i.getSlotsNumber();
		}
		return Result;
	}

	public int Nb_0N() {
		return getCalendrierNantes().getNb_Creneaux() - Nb_cours();
	}

	public int Nb_0B() {
		return getCalendrierBrest().getNb_Creneaux() - Nb_cours();
	}

	public int Nb_0JourN() {
		return Nb_0N() / getCalendrierNantes().getNb_Jours();
	}

	public int Nb_0JourB() {
		return Nb_0B() / getCalendrierBrest().getNb_Jours();
	}

	public ArrayList<Integer> Traduction(ArrayList<Unavailability> a) {
		ArrayList<LocalDate> d = new ArrayList<LocalDate>();
		for (Unavailability el : a) {
			d.add((LocalDate.of(Integer.valueOf(el.getDate().substring(0, 4)),
					Integer.valueOf(el.getDate().substring(5, 7)), Integer.valueOf(el.getDate().substring(8, 10)))));
		}
		LocalDate local_debut = (LocalDate.of(Integer.valueOf(debut.substring(0, 4)),
				Integer.valueOf(debut.substring(5, 7)), Integer.valueOf(debut.substring(8, 10))));
		ArrayList<Integer> l_int = new ArrayList<Integer>();
		ArrayList<Integer> l_final = new ArrayList<Integer>();
		for (LocalDate date : d) {
			Duration dure = Duration.between(local_debut.atTime(0, 0), date.atTime(0, 0));
			Integer i = (int) (long) dure.toDays();
			System.out.println(i);
			l_int.add((i / 7) * 2 + (i % 7));
		}

		for (int i : l_int) {
			for (Unavailability el : a) {
				ArrayList<Integer> slot = el.getSlots();
				{
					for (Integer k : slot) {
						l_final.add(i * 6 + k);
					}
				}
			}
		}
		ArrayList<Integer> l_creneau = new ArrayList<Integer>();
		for (int l = 0; l < this.getCalendrier().getNb_Creneaux(); l++) {
			if (l_final.contains(l)) {
				l_creneau.add(0);
			} else {
				l_creneau.add(1);
			}
		}
		return l_creneau;
	}

	public String getDebut() {
		return debut;
	}

	public ArrayList<Module> getListe_Module() {
		return liste_modules;
	}

	public Calendrier getCalendrierNantes() {
		return calendrierNantes;
	}

	public Calendrier getCalendrierBrest() {
		return calendrierBrest;
	}

	public Calendrier getCalendrier() {
		return calendrier;
	}

}
