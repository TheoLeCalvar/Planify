package com.solver.model;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;

public class Donnee {

	private ArrayList<UE> Liste_UE;
	private Calendrier calendrierNantes;
	private Calendrier calendrierBrest;
	private String debut;
	private ArrayList<Module> liste_modules;

	public Donnee(ArrayList<Module> UE_A, ArrayList<Module> UE_B, ArrayList<Module> UE_C, int Nb_Semaines,
			ArrayList<Unavailability> unavailabilitiesN, ArrayList<Unavailability> unavailabilitiesB, String debut) {
		this.Liste_UE = new ArrayList<UE>(3);
		UE uea = new UE("UE_A", UE_A);
		UE ueb = new UE("UE_B", UE_B);
		UE uec = new UE("UE_C", UE_C);
		this.Liste_UE.add(uea);
		this.Liste_UE.add(ueb);
		this.Liste_UE.add(uec);
		this.debut = debut;
		this.calendrierNantes = new Calendrier(Nb_Semaines);
		this.calendrierBrest = new Calendrier(Nb_Semaines);
		this.calendrierNantes = new Calendrier(Nb_Semaines, Traduction(unavailabilitiesN));
		this.calendrierBrest = new Calendrier(Nb_Semaines, Traduction(unavailabilitiesB));
		this.liste_modules = new ArrayList<>();
		this.liste_modules.add(null);
		for (UE ue : this.Liste_UE) {
			for (Module module : ue.getListeModules()) {
				this.liste_modules.add(module);
			}
		}
	}

	public ArrayList<UE> getListe_UE() {
		return Liste_UE;
	}

	public ArrayList<Module> getListe_Module() {
		return liste_modules;
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

	public Calendrier getCalendrierNantes() {
		return calendrierNantes;
	}

	public Calendrier getCalendrierBrest() {
		return calendrierBrest;
	}

	// le return est le disponibilités ou l'indisponilités?
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
		// changer 120 avec le nombre de creneaux total ( utiliser class creneaux )
		for (int l = 0; l < this.getCalendrierNantes().getNb_Creneaux(); l++) {
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

}
