package com.solver.model;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;


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
	
	public Donnee() {
		this.calendrier = new Calendrier(14);
		
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

	public ArrayList<Integer> Traduction(ArrayList<Unavailable> a, String debut ){
		ArrayList<LocalDate> d = new ArrayList<LocalDate>();
		for(Unavailable el :a) {
		d.add((LocalDate.of(Integer.valueOf(el.getDate().substring(0,4)),Integer.valueOf(el.getDate().substring(5,7)), Integer.valueOf(el.getDate().substring(8,10)))));
		}
		LocalDate local_debut= (LocalDate.of(Integer.valueOf(debut.substring(0,4)),Integer.valueOf(debut.substring(5,7)), Integer.valueOf(debut.substring(8,10))));
		ArrayList<Integer> l_int= new ArrayList<Integer>();
		ArrayList<Integer> l_final= new ArrayList<Integer>();
		for(LocalDate date:d) {
			Duration dure = Duration.between(local_debut.atTime(0, 0), date.atTime(0, 0));
			Integer i = (int) (long) dure.toDays();
			System.out.println(i);
			l_int.add((i/7)*2+(i%7));
		}

			for(int i:l_int) {
				for (Unavailable el : a) {
					ArrayList<Integer> slot =el.getSlots();
				 {
					for(Integer k : slot) {
					l_final.add(i*6+k);
				}
				}}}
			ArrayList<Integer>l_creneau=new ArrayList<Integer>();
			//changer 120 avec le nombre de creneaux total ( utiliser class creneaux )
			for(int l=0;l<this.getCalendrier().getNb_Creneaux();l++) {
					if(l_final.contains(l)) {
					l_creneau.add(0);
					}
					else {l_creneau.add(1);
				}
			}
			return l_creneau;
			}
public static void main(String args[]) {
	String s ="2022-12-21";
	String s2 ="2022-12-22";
	String s3 ="2022-12-28";

	ArrayList<Integer> i = new ArrayList<Integer>();
	i.add(2);
	i.add(4);
	Unavailable un = new Unavailable(s,i);
	Unavailable un1 = new Unavailable(s3,i);
	Unavailable un2 = new Unavailable(s2,i);

	
	ArrayList<Unavailable> e= new ArrayList<Unavailable>();
	e.add(un);
	e.add(un1);
	e.add(un2);
	String s1="2022-12-14";
	Donnee t = new Donnee();
		 System.out.println(t.Traduction(e,s1));
	}}
		

