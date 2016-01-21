package com.luxoft.bankapp.comands;

import java.sql.SQLException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luxoft.bankapp.dao.ClientDAO;
import com.luxoft.bankapp.dao.ClientDAOImpl;
import com.luxoft.bankapp.handling_exceptions.DAOException;
import com.luxoft.bankapp.handling_exceptions.NotEnoughFundsException;


public class WithdrawCommand implements Command {

	private final static Logger LOG = LoggerFactory.getLogger(WithdrawCommand.class);
	
	@Override
	public void execute() {
		Scanner scan=new Scanner(System.in);
		System.out.println("How much you want to withdraw?");
		String withdraw = scan.nextLine();
		float ammount= new Float(withdraw);
		ClientDAO clientDAO = new ClientDAOImpl();
		try {
			BankCommander.currentClient.getActiveAccount().withdraw(ammount);
			clientDAO.save(BankCommander.currentClient);
		} catch (NotEnoughFundsException e) {
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		LOG.debug("{} withdrawed from {} account", ammount, BankCommander.currentClient.getName());
		
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Withdraw funds from the client account");

	}

}


