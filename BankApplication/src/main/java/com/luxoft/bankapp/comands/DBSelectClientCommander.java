package com.luxoft.bankapp.comands;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;

import com.luxoft.bankapp.dao.ClientDAO;
import com.luxoft.bankapp.handling_exceptions.DAOException;

public class DBSelectClientCommander extends Command {


	private ClientDAO clientDAO;
	
	public ClientDAO getClientDAO() {
		return clientDAO;
	}

	@Autowired
	public void setClientDAO(ClientDAO clientDAO) {
		this.clientDAO = clientDAO;
	}

	@Override
	public void execute() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Pass Client name");
		String selectedClientName = scan.nextLine();


		try {
			while (clientDAO.findClientByName(BankCommander.currentBank, selectedClientName) == null) {
				System.out.println("No such client. Pass another name");
				selectedClientName = scan.nextLine();
			}
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
