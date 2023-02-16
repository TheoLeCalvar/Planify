package com.solver.model;

import java.util.ArrayList;

public class Request {

	private int weeksNumber;
	private String startDate;
	private ArrayList<Module> modulesUeA;
	private ArrayList<Module> modulesUeB;
	private ArrayList<Module> modulesUeC;
	private ArrayList<Unavailability> unavailables;

	public Request(int weeksNumber, ArrayList<Module> modulesUeA, ArrayList<Module> modulesUeB,
			ArrayList<Module> modulesUeC, ArrayList<Unavailability> unavailables, String startDate) {
		super();
		this.weeksNumber = weeksNumber;
		this.modulesUeA = modulesUeA;
		this.modulesUeB = modulesUeB;
		this.modulesUeC = modulesUeC;
		this.unavailables = unavailables;
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

	public ArrayList<Unavailability> getUnavailables() {
		return unavailables;
	}

}
