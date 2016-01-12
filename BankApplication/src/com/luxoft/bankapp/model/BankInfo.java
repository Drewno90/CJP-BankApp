package com.luxoft.bankapp.model;

import java.io.Serializable;

public class BankInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1478061135815033619L;
	private Bank bank;

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public String printReport() {
		int accountsNumber=0;
		for(Client client:bank.getClients())
			accountsNumber+=client.getAccounts().size();
		
		float credit=0;
		for(Client client:bank.getClients())
			for(Account account: client.getAccounts())
					if(account.getBalance()<0)
						credit+=account.getBalance();
		System.out.println("Bank credit sum: " + -credit);
		
	  	return "Bank has " + bank.getClients().size() + " clients. " + " Bank has " + accountsNumber + " accounts. " + "Bank credit sum: " + -credit ;

	}
	
}
