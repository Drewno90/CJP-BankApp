package com.luxoft.bankapp.handling_exceptions;

public class BankNotFoundException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2369108015823121300L;
	private String name;
	public BankNotFoundException(String name) {
		this.setName(name);
	}

	public void printMessage() {
        System.out.println("");
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
