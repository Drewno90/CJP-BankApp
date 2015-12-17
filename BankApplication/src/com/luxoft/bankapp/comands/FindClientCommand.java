package com.luxoft.bankapp.comands;

import java.util.Scanner;

import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.BankServiceImpl;

public class FindClientCommand implements Command {

	@Override
	public void execute() {
		Scanner scan=new Scanner(System.in);
		System.out.println("Enter Client name");
		String clientName=scan.nextLine();
		BankService bankService= new BankServiceImpl();
		
		bankService.getClient(BankCommander.currentBank, clientName);
		
		scan.close();
	}
	
	@Override
	public void printCommandInfo() {
		System.out.println("Find client by his name");
	}

}

