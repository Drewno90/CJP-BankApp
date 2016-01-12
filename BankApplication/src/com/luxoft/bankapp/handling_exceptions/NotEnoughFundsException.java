package com.luxoft.bankapp.handling_exceptions;

public class NotEnoughFundsException extends BankException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5717001019729470240L;
	private float amount;

	public float getAmount() {
		return amount;
	}

	public NotEnoughFundsException(float amount)
	{
		this.amount=amount;
	}
	
}



