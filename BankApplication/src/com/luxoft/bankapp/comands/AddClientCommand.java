package com.luxoft.bankapp.comands;

import java.sql.SQLException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luxoft.bankapp.dao.ClientDAO;
import com.luxoft.bankapp.dao.ClientDAOImpl;
import com.luxoft.bankapp.handling_exceptions.ClientExistsException;
import com.luxoft.bankapp.handling_exceptions.DAOException;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Gender;
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
		
		System.out.println("Enter Client city");
		String city=scan.nextLine();
		if(!city.matches("^[A-Z]{1}[a-z]{2,}$"))
			LOG.warn("Incorrect city");
		
		System.out.println("Enter Client gender");
		String genderS=scan.nextLine();
		if(!genderS.matches("^[A-Z]{1}[a-z]{2,}$"))
			LOG.warn("Incorrect gender");
		Gender gender;
		if(genderS.equals("male"))
			gender = Gender.MALE;
		else if(genderS.equals("male"))
			gender = Gender.FEMALE;
		else
		{
			LOG.warn("Incorrect gender");
			gender=null;
		}
		System.out.println("Enter Client bank id");
		String bank_id=scan.nextLine();
		if(!bank_id.matches("^[0-9]{1,}$"))
			LOG.warn("Incorrect bankID");
		int bankId= new Integer(bank_id);	
		
		Client client = new Client(name, overdraft, mailAddress, phoneNumber, city, gender, bankId); 
		
		BankService bankService= new BankServiceImpl();
		ClientDAO clientDAO = new ClientDAOImpl();
		try {
			bankService.addClient(BankCommander.currentBank, client);
			clientDAO.add(client);
			BankCommander.currentClient=client;
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (ClientExistsException e) {
			LOG.warn("This client already exists!");
			e.printStackTrace();
		}
		
			LOG.debug("Client {} added", client.getName());

	}

	@Override
	public void printCommandInfo() {
		System.out.println("Register the new client by entering data about him");
	}

}


