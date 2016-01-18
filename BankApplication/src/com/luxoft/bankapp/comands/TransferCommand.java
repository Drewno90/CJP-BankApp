package com.luxoft.bankapp.comands;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.BankServiceImpl;



public class TransferCommand implements Command {

	private final static Logger LOG = LoggerFactory.getLogger(TransferCommand.class);
	
	@Override
	public void execute() {
		Scanner scan=new Scanner(System.in);
		System.out.println("To whom you want to send?");
		String clientName = scan.nextLine();
		BankService bankService= new BankServiceImpl();
		
		Client clientToWhomTransfer = bankService.findClientByHisName(BankCommander.currentBank, clientName);

		System.out.println("How much you want to transfer?");
		String transfer = scan.nextLine();
		int ammount= new Integer(transfer);
		
		bankService.transfer(BankCommander.currentClient, clientToWhomTransfer, ammount);
		LOG.debug("{} transfered from {} to {}", ammount, BankCommander.currentClient.getName(), clientToWhomTransfer.getName());

	}

	@Override
	public void printCommandInfo() {
		System.out.println("Make the transfer from one client account to another client account of the same bank");

	}

}


