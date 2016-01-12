package com.luxoft.bankapp.requests;

import com.luxoft.bankapp.bank_application.BankServer;
import com.luxoft.bankapp.model.Bank;



public class BalanceRequest  implements Request {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1572780270699243195L;
	String clientName;
	private Bank bank;

	
	public BalanceRequest(String clientName, Bank bank)
	{
		this.clientName=clientName;
		this.bank=bank;
	}
	
	public void printInfo() {
		System.out.println("Show balance");

	}

	@Override
	public String execute(){
		float balance = BankServer.bankService.getClient(bank, clientName).getBalance();
		return "Your Balance is " + balance;
		
	}


}
