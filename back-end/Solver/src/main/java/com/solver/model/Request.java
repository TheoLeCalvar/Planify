package com.solver.model;

import java.util.ArrayList;

public class Request {
	
	private int weeksNumber;
	private ArrayList<Module> modulesUeA;
	private ArrayList<Module> modulesUeB;
	private ArrayList<Module> modulesUeC;
	private ArrayList<String> unavailable;
	
	public int getWeeksNumber() {
		return weeksNumber;
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

	public ArrayList<String> getUnavailable() {
		return unavailable;
	}
	
}
