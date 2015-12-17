package com.luxoft.bankapp.model;

import com.luxoft.bankapp.handling_exceptions.NotEnoughFundsException;

public interface Account extends Report{

	public float getBalance();
	public void deposit(float ammount);
	public void withdraw(float ammount) throws NotEnoughFundsException;
	public void decimalValue();
}



