package com.luxoft.bankapp.comands;

import java.util.Scanner;

import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.BankServiceImpl;



public class TransferCommand implements Command {

	
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
		
		scan.close();
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Make the transfer from one client account to another client account of the same bank");

	}

}

