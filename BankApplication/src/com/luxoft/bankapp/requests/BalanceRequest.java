package com.luxoft.bankapp.requests;

import com.luxoft.bankapp.comands.BankCommander;
import com.luxoft.bankapp.service.BankService;



public class BalanceRequest  implements Request {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1572780270699243195L;
	String clientName;
	BankService bankService;
	
	public BalanceRequest(String clientName,  BankService bankService)
	{
		this.clientName=clientName;
		this.bankService=bankService;
	}
	
	public void printInfo() {
		System.out.println("Show balance");

	}

	@Override
	public String execute(){
		float balance = bankService.findClientByHisName(BankCommander.currentBank, clientName).getActiveAccount().getBalance();
		return "Your Balance is " + balance;
		
	}


}

