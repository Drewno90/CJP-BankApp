package com.luxoft.bankapp.comands;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.luxoft.bankapp.handling_exceptions.ClientExistsException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.BankServiceImpl;

public class BankCommander {
	public static Bank currentBank = new Bank("My Bank");
	public static Client currentClient;
	
	private Map<Integer, Command> commandsMap;

	static Logger logger = Logger.getLogger(BankCommander.class.getName());

	static {
		logger.setLevel(Level.SEVERE);
	}
	
	public Map<Integer, Command> getCommandsMap() {
		return commandsMap;
	}
	@Autowired
	public void setCommandsMap(Map<Integer, Command> commands) {
		commandsMap = commands;
	}
	
	public  void registerCommand(Integer id, Command command) {
		commandsMap.put(id, command);
	}

	public void removeCommand(Integer id) {
		commandsMap.remove(id);
	}

	public void run() {

        BankService bankService = new BankServiceImpl();

		try {
			LogManager.getLogManager().readConfiguration(new FileInputStream("logger_all.properties"));
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		logger.info("System started");
		try {
			Client client1 = new Client("Ben", 1700, Gender.MALE, "Krakow");
			Account checkingAccount1 = new CheckingAccount(300, client1);
			bankService.addAccount(client1, checkingAccount1);
			bankService.setActiveAccount(client1, checkingAccount1);
			bankService.addClient(currentBank, client1);
		} catch (ClientExistsException e) {

			e.printStackTrace();
		}
		Scanner scan = new Scanner(System.in);
		while (true) {
			for (int i = 0; i < commandsMap.size(); i++) { // show menu
                System.out.print((i) + ") ");
                commandsMap.get(i).printCommandInfo();
			}
			int commandName = scan.nextInt();
			commandsMap.get(commandName).execute();

		}

	}
	
	public static void main(String args[])
	{
        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
		BankCommander bankCommander = (BankCommander) context.getBean("bankCommander");
		//BankCommander bankCommander= new BankCommander();
		bankCommander.run();
	}
}
