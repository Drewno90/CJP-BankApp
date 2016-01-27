package com.luxoft.bankapp.requests;

import com.luxoft.bankapp.comands.BankCommander;
import com.luxoft.bankapp.model.Client;

public class ClientAddRequest implements Request {

	/**
	 * 
	 */
	private static final long serialVersionUID = 366307583581186035L;
	private Client client;

	public ClientAddRequest(Client client) {
		this.client = client;
	}

	@Override
	public void printInfo() {
		System.out.println("Add client");

	}

	@Override
	public String execute() {

		BankCommander.currentBank.addClientToClientList(client);
		return "Client added ";

	}

}
