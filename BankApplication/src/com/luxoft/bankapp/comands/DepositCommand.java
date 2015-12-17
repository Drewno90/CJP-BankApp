package com.luxoft.bankapp.comands;

import java.util.Scanner;

import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.BankServiceImpl;

public class DepositCommand implements Command {


	@Override
	public void execute() {
		Scanner scan=new Scanner(System.in);
		System.out.println("How much you want to deposit?");
		String deposit = scan.nextLine();
		int ammount= new Integer(deposit);
		BankService bankService= new BankServiceImpl();
		bankService.deposit(BankCommander.currentClient, ammount);
		scan.close();
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Deposit the client account ");
	}

}

