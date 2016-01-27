package com.luxoft.bankapp.comands;

import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.BankServiceImpl;

public class GetAccountCommand implements Command {

	@Override
	public void execute() {

		BankService bankService = new BankServiceImpl();
		bankService.getAccounts(BankCommander.currentClient);

	}

	@Override
	public void printCommandInfo() {
		System.out.println("Get the list of the client accounts and the balance on each account");

	}

}
