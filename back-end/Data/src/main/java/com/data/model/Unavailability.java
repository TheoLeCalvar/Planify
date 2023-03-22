package com.data.model;

import java.util.ArrayList;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Unavailability {

	@NotBlank(message = "date is mandatory")
	private String date;

	@NotNull(message = "slots is mandatory")
	private ArrayList<Integer> slots;

	public String getDate() {
		return date;
	}

	public ArrayList<Integer> getSlots() {
		return slots;
	}

}
