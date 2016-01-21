package com.luxoft.bankapp.comands;

import java.util.Scanner;

import com.luxoft.bankapp.dao.ClientDAO;
import com.luxoft.bankapp.dao.ClientDAOImpl;
import com.luxoft.bankapp.handling_exceptions.DAOException;

public class DBSelectClientCommander implements Command {

	@Override
	public void execute() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Pass Client name");
		String selectedClientName = scan.nextLine();
        ClientDAO clientDAO = new ClientDAOImpl();
        
        try {	
        BankCommander.currentClient = clientDAO.findClientByName(BankCommander.currentBank, selectedClientName);
        
    } catch (DAOException e) {
        e.printStackTrace();
    }
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Set active Client");
		
	}

}
