package com.luxoft.bankapp.comands;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.BankServiceImpl;

public class DepositCommand implements Command {

	private final static Logger LOG = LoggerFactory.getLogger(DepositCommand.class);

	@Override
	public void execute() {
		Scanner scan=new Scanner(System.in);
		System.out.println("How much you want to deposit?");
		String deposit = scan.nextLine();
		int ammount= new Integer(deposit);
		BankService bankService= new BankServiceImpl();
		bankService.deposit(BankCommander.currentClient, ammount);
		LOG.debug("{} deposited on {} account", ammount, BankCommander.currentClient.getName());

	}

	@Override
	public void printCommandInfo() {
		System.out.println("Deposit the client account ");
	}

}


