package com.luxoft.bankapp.requests;

public class LogOutRequest implements Request {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6821968058017707580L;

	@Override
	public void printInfo() {
		System.out.println("Log Out");

	}

	@Override
	public String execute() {
		return ("Logged out");
	}

}
