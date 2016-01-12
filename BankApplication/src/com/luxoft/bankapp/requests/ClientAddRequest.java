package com.luxoft.bankapp.requests;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;


public class ClientAddRequest implements Request {

	/**
	 * 
	 */
	private static final long serialVersionUID = 366307583581186035L;
	private Client client;
	private Bank bank;

	public ClientAddRequest(Client client, Bank bank)
	{
		this.client = client;
		this.bank = bank;
	}

	@Override
	public void printInfo() {
		System.out.println("Add client");

	}
	
	@Override
	public String execute() {
		
		bank.addClientToClientList(client);
	  	return "Client added ";

	}

}
