package com.luxoft.bankapp.requests;

import com.luxoft.bankapp.bank_application.BankServer;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;


public class ClientRemoveRequest implements Request {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1794865730898738850L;
	private Bank bank;
	private Client client;
	private String clientName;

	public ClientRemoveRequest(String clientName, Bank bank)
	{
		this.clientName = clientName;
		this.bank = bank;
	}
	
	@Override
	public void printInfo() {
		System.out.println("Remove client");

	}

	@Override
	public String execute() {
		client = BankServer.bankService.findClientByHisName(bank, clientName);
		bank.removeClientFromList(client);
	  	return "Client deleted ";

	}

}
