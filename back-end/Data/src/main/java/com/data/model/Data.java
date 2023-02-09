package com.data.model;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;

public class Data {

	@Id
	private String id;
	private String creationDate;
	private int weeksNumber;
	private String startDate;
	private ArrayList<Module> modulesUeA;
	private ArrayList<Module> modulesUeB;
	private ArrayList<Module> modulesUeC;
	private ArrayList<Unavailable> unavailables;
	private String calendar;
	private ArrayList<String> teacherWaitingList;

	public String getId() {
		return id;
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

	public String getCalendar() {
		return calendar;
	}

	public void setCalendar(String calendar) {
		this.calendar = calendar;
	}

}
