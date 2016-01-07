package com.luxoft.bankapp.model;

public enum Gender {
	MALE("Mr."),
	FEMALE("Ms.");
	
	private String salut;

	Gender(final String salut) {
		this.salut = salut;
	}

	public String getSalute() {
		return salut;
	}
}


