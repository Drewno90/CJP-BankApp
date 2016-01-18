package com.luxoft.bankapp.requests;

import com.luxoft.bankapp.comands.BankCommander;
import com.luxoft.bankapp.service.BankServiceImpl;

public class ClientInfoRequest implements Request{


	/**
	 * 
	 */
	private static final long serialVersionUID = -4824088526708657801L;
	private String clientName;
	private BankServiceImpl bankService;
	
	public ClientInfoRequest(String clientName, BankServiceImpl bankService) {
		this.bankService=bankService;
		this.clientName=clientName;
	}

	@Override
	public void printInfo() {
		System.out.println("Get information about client");
		
	}

	@Override
	public String execute() {
		BankCommander.currentClient=bankService.findClientByHisName(BankCommander.currentBank, clientName);
	  	return new StringBuffer().append("Client name=").append(BankCommander.currentClient.getName()).append(", accounts=").append(BankCommander.currentClient.getAccounts()).append(", activeAccount=").append(BankCommander.currentClient.getActiveAccount()).append(", initialOverdraft=").append(BankCommander.currentClient.getInitialOverdraft()).append(", clientGender=").append(BankCommander.currentClient.getClientGender()).toString();

	}

}

