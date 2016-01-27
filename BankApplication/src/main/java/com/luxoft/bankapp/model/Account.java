package com.luxoft.bankapp.model;

import java.io.Serializable;
import java.util.Map;

import com.luxoft.bankapp.handling_exceptions.NotEnoughFundsException;

public interface Account extends Report, Serializable {

	public float getBalance();

	public void deposit(float ammount);

	public void withdraw(float ammount) throws NotEnoughFundsException;

	public void decimalValue();

	public String getAccountType();

	public void parseFeed(Map<String, String> feed);

	public int getClientId();

}
