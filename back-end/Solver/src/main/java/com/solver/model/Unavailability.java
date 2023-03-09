package com.solver.model;

import java.util.ArrayList;

public class Unavailability {

	private String date;
	private ArrayList<Integer> slots;

	public Unavailability() {
		super();
	}

	public Unavailability(String date, ArrayList<Integer> slots) {
		this.date = date;
		this.slots = slots;
	}

	public String getDate() {
		return date;
	}

	public ArrayList<Integer> getSlots() {
		return slots;
	}

}
