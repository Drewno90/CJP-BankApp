package com.luxoft.bankapp.comands;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.BankServiceImpl;

public class WithdrawCommand implements Command {

	private final static Logger LOG = LoggerFactory.getLogger(WithdrawCommand.class);
	
	@Override
	public void execute() {
		Scanner scan=new Scanner(System.in);
		System.out.println("How much you want to withdraw?");
		String withdraw = scan.nextLine();
		float ammount= new Float(withdraw);
		BankService bankService= new BankServiceImpl();
		
		bankService.withdraw(BankCommander.currentClient, ammount);
		LOG.debug("{} withdrawed from {} account", ammount, BankCommander.currentClient.getName());
		
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Withdraw funds from the client account");

	}

}

