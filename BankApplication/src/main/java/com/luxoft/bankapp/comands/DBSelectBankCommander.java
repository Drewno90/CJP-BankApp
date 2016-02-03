package com.luxoft.bankapp.comands;

import java.util.Scanner;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.luxoft.bankapp.dao.BankDAO;
import com.luxoft.bankapp.dao.ClientDAO;
import com.luxoft.bankapp.handling_exceptions.BankNotFoundException;
import com.luxoft.bankapp.handling_exceptions.DAOException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

public class DBSelectBankCommander extends Command {
	
	@Autowired
	private BankDAO bankDAO;
	@Autowired
	private ClientDAO clientDAO;
	
	public BankDAO getBankDAO() {
		return bankDAO;
	}

	public void setBankDAO(BankDAO bankDAO) {
		this.bankDAO = bankDAO;
	}

	public ClientDAO getClientDAO() {
		return clientDAO;
	}

	public void setClientDAO(ClientDAO clientDAO) {
		this.clientDAO = clientDAO;
	}


	
	@Override
	public void execute() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Pass bank name");
		String selectedBankName = scan.nextLine();

		Set<Client> bankClientsList;
		try {
			while (bankDAO.getBankByName(selectedBankName) == null) {
				System.out.println("No such Bank. Pass another name");
				selectedBankName = scan.nextLine();
			}
			Bank bank = bankDAO.getBankByName(selectedBankName);
			bankClientsList = clientDAO.getAllClients(bank);
			BankCommander.currentBank = bank;
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
