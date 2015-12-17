package com.luxoft.bankapp.comands;

import java.util.Scanner;

import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.BankServiceImpl;

public class WithdrawCommand implements Command {

	
	@Override
	public void execute() {
		Scanner scan=new Scanner(System.in);
		System.out.println("How much you want to withdraw?");
		String withdraw = scan.nextLine();
		int ammount= new Integer(withdraw);
		BankService bankService= new BankServiceImpl();
		
		bankService.withdraw(BankCommander.currentClient, ammount);
		
		
		scan.close();
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Withdraw funds from the client account");

	}

}

