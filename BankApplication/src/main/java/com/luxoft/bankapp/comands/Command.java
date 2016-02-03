package com.luxoft.bankapp.comands;

import org.springframework.beans.factory.annotation.Autowired;

import com.luxoft.bankapp.service.BankService;

public abstract class Command {
	
	@Autowired
	protected BankService bankService;
	
	public BankService getBankService() {
		return bankService;
	}

	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}
	
	public abstract void execute();

	public abstract void printCommandInfo();
}
