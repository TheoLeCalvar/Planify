package com.data.model;

import org.springframework.data.annotation.Id;

public class Data {

	@Id
	private String id;
	private int slotsNumber;
//	TODO: ajouter de variables
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getSlotsNumber() {
		return slotsNumber;
	}
	public void setSlotsNumber(int slotsNumber) {
		this.slotsNumber = slotsNumber;
	}
	
}
