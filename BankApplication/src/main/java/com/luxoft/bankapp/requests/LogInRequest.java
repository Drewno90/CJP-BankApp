package com.luxoft.bankapp.requests;

public class LogInRequest implements Request {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3261465003831534472L;
	String clientName;

	public LogInRequest(String name) {
		this.clientName = name;
	}

	@Override
	public void printInfo() {
		System.out.println("Log in");
	}

	@Override
	public String execute() {
		return "Logged in";
	}

}
