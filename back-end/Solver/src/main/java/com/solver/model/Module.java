package com.solver.model;

import java.util.Map;

import com.solver.util.Localisation;

public class Module {

	private String name;
	private int slotsNumber;
	private Map<Localisation, String> mails;
	private boolean isSync;

	public Module(String name, int slotsNumber, Map<Localisation, String> mails, boolean isSync) {
		super();
		this.name = name;
		this.slotsNumber = slotsNumber;
		this.mails = mails;
		this.isSync = isSync;
	}

	public String getName() {
		return name;
	}

	public int getSlotsNumber() {
		return slotsNumber;
	}

	public Map<Localisation, String> getMails() {
		return mails;
	}

	public boolean getIsSync() {
		return isSync;
	}

	public int getEtalement_semaines() {
		return 1; // this.getUserB().getEtalement_semaines();
	}

	public boolean Creneaux_dispoN(int i) { // Prend en entrée un créneaux et vérifie si il est dispo==true ou
											// non==false
//		if (this.getDispoN().get(i) == 1) {
//			return true;
//		} else {
//			return false;
//		}
		return true;
	}

	public boolean Creneaux_dispoB(int i) { // Prend en entrée un créneaux et vérifie si il est dispo==true ou
											// non==false
//		if (this.getDispoB().get(i) == 1) {
//			return true;
//		} else {
//			return false;
//		}
		return true;
	}

}
