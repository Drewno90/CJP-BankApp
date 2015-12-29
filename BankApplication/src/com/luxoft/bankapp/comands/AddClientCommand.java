package com.luxoft.bankapp.comands;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luxoft.bankapp.handling_exceptions.ClientExistsException;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.BankServiceImpl;


public class AddClientCommand implements Command {

	private final static Logger LOG = LoggerFactory.getLogger(AddClientCommand.class);
	
	@Override
	public void execute() {
		
		Scanner scan=new Scanner(System.in);

		System.out.println("Enter Client name and surname");
		String name=scan.nextLine();
		if(!name.matches("^[A-Z]{1}[a-z]{2,}\\s[A-Z]{1}[a-z]{2,}$"))
			LOG.warn("Incorrect name");
		
		System.out.println("Enter Client mail address");
		String mailAddress=scan.nextLine();
		if(!mailAddress.matches("^[A-Za-z\\.-0-9]{2,}@[A-Za-z\\.-0-9]{2,}\\.[A-Za-z]{2,3}$"))
			LOG.warn("Incorrect email address");
		
		System.out.println("Enter Client phone number");
		String phoneNumber=scan.nextLine();
		if(!phoneNumber.matches("^[0-9]{9}$"))
			LOG.warn("Incorrect phone number");
		
		System.out.println("Enter Client overdraft");
		String input=scan.nextLine();
		if(!input.matches("^[0-9]{1,}$"))
			LOG.warn("Incorrect amount");
		int overdraft= new Integer(input);	
		
		Client client = new Client(name, mailAddress, phoneNumber, overdraft); 
		
		BankService bankService= new BankServiceImpl();
		try {
			bankService.addClient(BankCommander.currentBank, client);
			LOG.debug("Client {} added", client.getName());
		} catch (ClientExistsException e) {
			LOG.warn("This client already exists!");
			e.printStackTrace();
		}
		
		//scan.close();
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Register the new client by entering data about him");
	}

}

