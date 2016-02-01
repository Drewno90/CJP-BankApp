package com.luxoft.bankapp.handling_exceptions;

public class FeedException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7455542966307279626L;

	public FeedException(String message) {
         super(message);
    }
}

