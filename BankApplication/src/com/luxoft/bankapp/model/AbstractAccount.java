package com.luxoft.bankapp.model;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luxoft.bankapp.handling_exceptions.NotEnoughFundsException;

public abstract class AbstractAccount implements Account, Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 671200949153231095L;

	private final static Logger LOG = LoggerFactory.getLogger(AbstractAccount.class);

	private float balance;
	
	AbstractAccount(float balance)
	{
		if(balance<0)
		{
			LOG.warn("Illegal argument {}", balance);
			throw new IllegalArgumentException(Float.toString(balance));
		}
		this.balance=balance;
	}
	
	@Override
	public float getBalance() {
		return balance;
	}
	
	public void setBalance(float balance) {
		this.balance = balance;
	}
	
	@Override
	public void deposit(float amount) {
		this.balance+=amount;
	}

	@Override
	public void withdraw(float amount) throws NotEnoughFundsException {
		if(this.balance>=amount)
			this.balance-=amount;
		else 
		{
			LOG.warn("Not Enough Funds");
			throw new NotEnoughFundsException(balance);
		}
	}

	@Override
	public void printReport() {
		System.out.println("This is " + this.getClass() + " and its balance is " + this.balance);
	}
	
	@Override
	public void decimalValue()
	{
		LOG.debug("{}",Math.round(balance));
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(balance);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractAccount other = (AbstractAccount) obj;
		if (Float.floatToIntBits(balance) != Float.floatToIntBits(other.balance))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AbstractAccount balance=" + balance ;
	}


}



