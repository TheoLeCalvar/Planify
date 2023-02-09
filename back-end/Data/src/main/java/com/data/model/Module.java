package com.data.model;

import java.util.Map;

import com.data.util.Localisation;

public class Module {

	private String name;
	private int slotsNumber;
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
