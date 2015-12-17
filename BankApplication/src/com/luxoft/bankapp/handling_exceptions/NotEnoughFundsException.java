package com.luxoft.bankapp.handling_exceptions;

public class NotEnoughFundsException extends BankException{

	private float amount;

	public float getAmount() {
		return amount;
	}

	public NotEnoughFundsException(float amount)
	{
		this.amount=amount;
	}
	
}



