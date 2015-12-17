package com.luxoft.bankapp.comands;

import java.util.Scanner;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

class BankCommander {
public static Bank currentBank = new Bank("MyBank");
public static Client currentClient;
        
    static Command[] commands = {
        new FindClientCommand(), // 1
        new GetAccountCommand(), // 2
        new DepositCommand(),	//3
        new WithdrawCommand(),   //4
        new TransferCommand(),	//5
        new AddClientCommand(),
        new Command() { // 7 - Exit Command
            public void execute() {
                System.exit(0);
            }
            public void printCommandInfo() {
                System.out.println("Exit");
            }
        }
    };
    
    public static void main(String args[]) {
        while (true) {
           for (int i=0;i<commands.length;i++) { // show menu
                 System.out.print(i+") ");
                 commands[i].printCommandInfo();
           }
           Scanner scan=new Scanner(System.in);
           String commandString = scan.nextLine();
           int command= new Integer(commandString); // initialize command with commandString
           commands[command].execute();
           scan.close();
        }
        
}
}

