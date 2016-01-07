package com.luxoft.bankapp.model;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SavingAccount extends AbstractAccount {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3918357958919593204L;

	private final static Logger LOG = LoggerFactory.getLogger(SavingAccount.class);
	
	private String accountType="SavingAccount";
	
	public SavingAccount(float balance) {
		super(balance);
	}

	public void setBalance(float balance) {
		if(balance<=0)
		{
			LOG.warn("Illegal argument");
			throw new IllegalArgumentException(Float.toString(balance));
		}
		setBalance(balance);
	}
	
	@Override
	public String toString() {
		return "SavingAccount - balance: " + this.getBalance();
	}

	@Override
	public void parseFeed(Map<String, String> feed) {
		this.accountType=feed.get("accountType");
		float balance = Float.parseFloat(feed.get("balance"));
		this.setBalance(balance);
	}


	@Override
	public String getAccountType() {
		return accountType;
	}
	
}



