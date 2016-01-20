package com.luxoft.bankapp.comands;

import java.sql.SQLException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luxoft.bankapp.dao.ClientDAO;
import com.luxoft.bankapp.dao.ClientDAOImpl;
import com.luxoft.bankapp.handling_exceptions.DAOException;
import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.BankServiceImpl;

public class DepositCommand implements Command {

	private final static Logger LOG = LoggerFactory.getLogger(DepositCommand.class);

	@Override
	public void execute() {
		Scanner scan=new Scanner(System.in);
		System.out.println("How much you want to deposit?");
		String deposit = scan.nextLine();
		int ammount= new Integer(deposit);
		BankService bankService= new BankServiceImpl();
		ClientDAO clientDAO = new ClientDAOImpl();
		try {
			bankService.findClientByHisName(BankCommander.currentBank, BankCommander.currentClient.getName()).getActiveAccount().deposit(ammount);
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


