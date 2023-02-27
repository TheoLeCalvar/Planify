package com.solver.model;

import java.util.ArrayList;
import java.util.Map;

import com.solver.util.Localisation;

public class Request {

	private int weeksNumber;
	private String startDate;
	private ArrayList<Module> modulesUeA;
	private ArrayList<Module> modulesUeB;
	private ArrayList<Module> modulesUeC;
	private Map<Localisation, ArrayList<Unavailability>> unavailabilities;

	public Request(int weeksNumber, ArrayList<Module> modulesUeA, ArrayList<Module> modulesUeB,
			ArrayList<Module> modulesUeC, Map<Localisation, ArrayList<Unavailability>> unavailabilities,
			String startDate) {
		this.weeksNumber = weeksNumber;
		this.modulesUeA = modulesUeA;
		this.modulesUeB = modulesUeB;
		this.modulesUeC = modulesUeC;
		this.unavailabilities = unavailabilities;
		this.startDate = startDate;
	}

	public int getWeeksNumber() {
		return weeksNumber;
	}

	public String getStartDate() {
		return startDate;
	}

	public ArrayList<Module> getModulesUeA() {
		return modulesUeA;
	}

	public ArrayList<Module> getModulesUeB() {
		return modulesUeB;
	}

	public ArrayList<Module> getModulesUeC() {
		return modulesUeC;
	}

	public Map<Localisation, ArrayList<Unavailability>> getUnavailabilities() {
		return unavailabilities;
	}

}
