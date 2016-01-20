package com.luxoft.bankapp.comands;

import java.util.Scanner;
import java.util.Set;

import com.luxoft.bankapp.dao.ClientDAO;
import com.luxoft.bankapp.dao.BankDAO;
import com.luxoft.bankapp.dao.BankDAOImpl;
import com.luxoft.bankapp.dao.ClientDAOImpl;
import com.luxoft.bankapp.handling_exceptions.BankNotFoundException;
import com.luxoft.bankapp.handling_exceptions.DAOException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

public class DBRemoveClientCommander implements Command{

	@Override
	public void execute() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Pass bank name");
		String selectedBankName = scan.nextLine();
        BankDAO bankDAO = new BankDAOImpl();
        ClientDAO clientDAO = new ClientDAOImpl();
        Set<Client> bankClientsList;
        try {	
        Bank bank = bankDAO.getBankByName(selectedBankName);
        bankClientsList = clientDAO.getAllClients(bank);
        BankCommander.currentBank=bank;
        BankCommander.currentBank.setClientsList(bankClientsList);

    } catch (DAOException e) {
        e.printStackTrace();
    } catch (BankNotFoundException e) {
        e.printStackTrace();
    }
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Set Bank");
		
	}

}
