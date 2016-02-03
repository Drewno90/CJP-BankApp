package com.luxoft.bankapp.comands;

import java.util.Scanner;


public class FindClientCommand extends Command {

	@Override
	public void execute() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter Client name");
		String clientName = scan.nextLine();
		BankCommander.currentClient = bankService.getClient(BankCommander.currentBank, clientName);
		if (BankCommander.currentClient != null)
			System.out.println(BankCommander.currentClient.getName() + " founded!");
		else
			System.out.println("There is no such a client!");
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Find client by his name");
	}

}
