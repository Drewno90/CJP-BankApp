package com.luxoft.bankapp.comands;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

class BankCommander {
public static Bank currentBank = new Bank("MyBank");
public static Client currentClient;
public static Map<String, Command> commands= new TreeMap<String, Command>();



    public static void initializeCommands(){
    	commands.put("1", new FindClientCommand());
    	commands.put("2", new GetAccountCommand());
    	commands.put("3", new DepositCommand());
    	commands.put("4", new WithdrawCommand());
    	commands.put("5", new TransferCommand());
    	commands.put("6", new AddClientCommand());
    	commands.put("7", new Command() { // 7 - Exit Command
            public void execute() {
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
    	
    	initializeCommands();
        while (true) {
           for (String commandNumber: commands.keySet()) { // show menu
                 System.out.print(commandNumber+") ");
                 commands.get(commandNumber).printCommandInfo();
           }
           Scanner scan=new Scanner(System.in);
           String commandName = scan.nextLine();
           commands.get(commandName).execute();
           scan.close();
        }
        
}
}

