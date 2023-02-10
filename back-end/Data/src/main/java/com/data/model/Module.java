package com.data.model;

import java.util.Map;

import com.data.util.Localisation;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Module {

	@NotBlank(message = "name is mandatory")
	private String name;

	@Min(value = 1, message = "slotsNumber: minimum value is 1")
	private int slotsNumber;

	@NotNull(message = "mails is mandatory")
	private Map<Localisation, String> mails;

	private boolean isSync;

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

}
