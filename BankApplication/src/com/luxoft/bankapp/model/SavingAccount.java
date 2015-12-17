package com.luxoft.bankapp.model;

public class SavingAccount extends AbstractAccount{

	public SavingAccount(float balance) {
		super(balance);
	}

	@Override
	public String toString() {
		return "SavingAccount - balance: " + this.getBalance();
	}
	
	
}



