package com.luxoft.bankapp.comands;

import java.sql.SQLException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.luxoft.bankapp.dao.ClientDAO;
import com.luxoft.bankapp.handling_exceptions.DAOException;

public class DepositCommand extends Command {

	private final static Logger LOG = LoggerFactory.getLogger(DepositCommand.class);

	@Autowired
	private ClientDAO clientDAO;
	
	public ClientDAO getClientDAO() {
		return clientDAO;
	}

	public void setClientDAO(ClientDAO clientDAO) {
		this.clientDAO = clientDAO;
	}

	@Override
	public void execute() {
		Scanner scan = new Scanner(System.in);
		System.out.println("How much you want to deposit?");
		int ammount = scan.nextInt();
		

		try {

			BankCommander.currentClient.getActiveAccount().deposit(ammount);

			clientDAO.save(BankCommander.currentClient);
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		LOG.debug("{} deposited on {} account", ammount, BankCommander.currentClient.getName());

	}

	@Override
	public void printCommandInfo() {
		System.out.println("Deposit the client account ");
	}

}
