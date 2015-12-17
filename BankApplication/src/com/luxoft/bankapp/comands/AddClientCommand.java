package com.luxoft.bankapp.comands;

import java.util.Scanner;

import com.luxoft.bankapp.handling_exceptions.ClientExistsException;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.BankServiceImpl;


public class AddClientCommand implements Command {

	
	@Override
	public void execute() {
		
		Scanner scan=new Scanner(System.in);

		System.out.println("Enter Client name and surname");
		String name=scan.nextLine();
		if(!name.matches("^[A-Z]{1}[a-z]{2,}\\s[A-Z]{1}[a-z]{2,}$"))
			System.out.println("Incorrect name");
		
		System.out.println("Enter Client mail address");
		String mailAddress=scan.nextLine();
		if(!mailAddress.matches("^[A-Za-z\\.-0-9]{2,}@[A-Za-z\\.-0-9]{2,}\\.[A-Za-z]{2,3}$"))
			System.out.println("Incorrect email address");
		
		System.out.println("Enter Client phone number");
		String phoneNumber=scan.nextLine();
		if(!phoneNumber.matches("^[0-9]{9}$"))
			System.out.println("Incorrect phone number");
		
		System.out.println("Enter Client overdraft");
		String input=scan.nextLine();
		if(!input.matches("^[0-9]{1,}$"))
			System.out.println("Incorrect amount");
		int overdraft= new Integer(input);	
		
		Client client = new Client(name, mailAddress, phoneNumber, overdraft, Gender.MALE);
		
		BankService bankService= new BankServiceImpl();
		try {
			bankService.addClient(BankCommander.currentBank, client);
		} catch (ClientExistsException e) {
			System.out.println("This client already exists!");
			e.printStackTrace();
		}
		
		scan.close();
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Register the new client by entering data about him");
	}

}

