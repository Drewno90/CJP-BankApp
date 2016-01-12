package com.luxoft.bankapp.requests;

import com.luxoft.bankapp.bank_application.BankServer;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;


public class ClientInfoRequest implements Request{


	/**
	 * 
	 */
	private static final long serialVersionUID = -4824088526708657801L;
	private String clientName;
	private Bank bank;
	private Client client;

	public ClientInfoRequest(String clientName, Bank currentBank) {
		this.clientName=clientName;
		this.bank=currentBank;
	}

	@Override
	public void printInfo() {
		System.out.println("Get information about client");
		
	}

	@Override
	public String execute() {
		client = BankServer.bankService.findClientByHisName(bank, clientName);
		bank.removeClientFromList(client);
	  	return new StringBuffer().append("Client name=").append(client.getName()).append(", accounts=").append(client.getAccounts()).append(", activeAccount=").append(client.getActiveAccount()).append(", initialOverdraft=").append(client.getInitialOverdraft()).append(", clientGender=").append(client.getClientGender()).toString();

	}

}
