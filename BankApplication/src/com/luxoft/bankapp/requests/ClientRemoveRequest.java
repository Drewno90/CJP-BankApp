package com.luxoft.bankapp.requests;

import com.luxoft.bankapp.comands.BankCommander;
import com.luxoft.bankapp.service.BankService;


public class ClientRemoveRequest implements Request {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1794865730898738850L;

	private String clientName;
	private BankService bankService;
	public ClientRemoveRequest(String clientName, BankService bankService)
	{
		this.clientName = clientName;

		this.bankService=bankService;
	}
	
	@Override
	public void printInfo() {
		System.out.println("Remove client");

	}

	@Override
	public String execute() {
		BankCommander.currentClient = bankService.findClientByHisName(BankCommander.currentBank, clientName);
		BankCommander.currentBank.removeClientFromList(BankCommander.currentClient);
	  	return "Client deleted ";

	}

}

