package com.solver.model;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;

public class Unavailable {

	private String date;
	private ArrayList<Integer> slots;

	public Unavailable(String date, ArrayList<Integer> slots) {
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



