package com.solver.model;

import java.util.ArrayList;

public class Request {

	private int weeksNumber;
	private String startDate;
	private ArrayList<Module> modulesUeA;
	private ArrayList<Module> modulesUeB;
	private ArrayList<Module> modulesUeC;
	private ArrayList<Unavailable> unavailables;

	public Request(int weeksNumber, ArrayList<Module> modulesUeA, ArrayList<Module> modulesUeB,
			ArrayList<Module> modulesUeC, ArrayList<Unavailable> unavailables) {
		super();
		this.weeksNumber = weeksNumber;
		this.modulesUeA = modulesUeA;
		this.modulesUeB = modulesUeB;
		this.modulesUeC = modulesUeC;
		this.unavailables = unavailables;
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

	public ArrayList<Unavailable> getUnavailables() {
		return unavailables;
	}

}
