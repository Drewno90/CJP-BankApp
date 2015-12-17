package com.luxoft.bankapp.model;

public class SavingAccount extends AbstractAccount{

	public SavingAccount(float balance) {
		super(balance);
	}

	public void setBalance(float balance) {
		if(balance<=0)
		{
			System.out.println("Illegal argument");
			throw new IllegalArgumentException(Float.toString(balance));
		}
		setBalance(balance);
	}
	
	@Override
	public String toString() {
		return "SavingAccount - balance: " + this.getBalance();
	}
	
	
}



