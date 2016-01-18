package com.luxoft.bankapp.comands;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luxoft.bankapp.handling_exceptions.ClientExistsException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.BankServiceImpl;

public class BankCommander {
public static Bank currentBank = new Bank("MyBank");
public static Client currentClient;
public static Map<String, Command> commands= new TreeMap<String, Command>();

private final static Logger LOG = LoggerFactory.getLogger(BankCommander.class);

    public static void initializeCommands(){
    	commands.put("1", new FindClientCommand());
    	commands.put("2", new GetAccountCommand());
    	commands.put("3", new DepositCommand());
    	commands.put("4", new WithdrawCommand());
    	commands.put("5", new TransferCommand());
    	commands.put("6", new AddClientCommand());
    	commands.put("7", new Command() { // 7 - Exit Command
            public void execute() {
        		LOG.info("System Stopped");
                System.exit(0);
            }
            public void printCommandInfo() {
                System.out.println("Exit");
            }
        });
    }
    
    public static void registerCommand(String name, Command command)
    {
    	commands.put(name, command);
    }
    
    public static void removeCommand(String name)
    {
    	commands.remove(name);
    }
    
    
    public static void main(String args[]) {
    	BankService bankService=new BankServiceImpl();
		LOG.info("System started");
		try {
			Client client1 = new Client("Ben",1700,Gender.MALE, "Krakow");
			Account checkingAccount1 = new CheckingAccount(300, client1);
			bankService.addAccount(client1,checkingAccount1);
			bankService.setActiveAccount(client1,checkingAccount1);
			bankService.addClient(currentBank, client1);
			} catch (ClientExistsException e) {

			e.printStackTrace();
		}
    	initializeCommands();
        Scanner scan=new Scanner(System.in);
        while (true) {
           for (String commandNumber: commands.keySet()) { // show menu
                 System.out.print(commandNumber+") ");
                 commands.get(commandNumber).printCommandInfo();
           }
           String commandName = scan.nextLine();
           commands.get(commandName).execute();


        }
        
}
}


