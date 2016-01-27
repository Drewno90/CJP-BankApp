package com.luxoft.bankapp.comands;

import java.sql.SQLException;

import com.luxoft.bankapp.dao.ClientDAO;
import com.luxoft.bankapp.dao.AccountDAO;
import com.luxoft.bankapp.dao.AccountDAOImpl;
import com.luxoft.bankapp.dao.ClientDAOImpl;
import com.luxoft.bankapp.handling_exceptions.DAOException;

public class DBRemoveClientCommander implements Command {

	@Override
	public void execute() {

		AccountDAO accountDAO = new AccountDAOImpl();
		ClientDAO clientDAO = new ClientDAOImpl();
		try {

			accountDAO.removeByClientId(BankCommander.currentClient.getId());
			clientDAO.remove(BankCommander.currentClient);

		} catch (DAOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Delete Active Client");

	}

}
