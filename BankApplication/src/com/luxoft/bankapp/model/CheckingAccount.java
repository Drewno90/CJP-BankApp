package com.luxoft.bankapp.model;

import com.luxoft.bankapp.handling_exceptions.OverDraftLimitExceededException;

public class CheckingAccount extends AbstractAccount{
	
	

	private float overdraft;
	
	public CheckingAccount(float balance) {
		super(balance);
		this.overdraft=0;
	}
	
	
	public float getOverdraft() {
		return overdraft;
	}


	public CheckingAccount(float balance, Client client) {
		super(balance);
		overdraft=client.getInitialOverdraft();
		if(overdraft<0)
		{
			System.out.println("Illegal argument");
			throw new IllegalArgumentException(Float.toString(overdraft));
		}
		if(balance<-overdraft)
		{
			System.out.println("Illegal argument");
			throw new IllegalArgumentException(Float.toString(balance));
		}	

	}
	
	
	public void setOverdraft(float overdraft) throws IllegalArgumentException
	{
		if(overdraft<0)
		{
			System.out.println("Illegal argument");
			throw new IllegalArgumentException(Float.toString(overdraft));
		}
		this.overdraft=overdraft;
	}

	@Override
	public void withdraw(float amount) throws OverDraftLimitExceededException {
		if((-overdraft)<=(getBalance()-amount))
			this.setBalance(getBalance()-amount);
		else
		{
			System.out.println("Overdraft Limit Exceeded");
			throw new OverDraftLimitExceededException( getBalance()-amount ,overdraft);
		}
	}

	
	@Override
	public String toString() {
		return "CheckingAccount - overdraft: " + this.getOverdraft() + " balance: " + this.getBalance() ;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Float.floatToIntBits(overdraft);
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CheckingAccount other = (CheckingAccount) obj;
		if (Float.floatToIntBits(overdraft) != Float.floatToIntBits(other.overdraft))
			return false;
		return true;
	}




	
}



