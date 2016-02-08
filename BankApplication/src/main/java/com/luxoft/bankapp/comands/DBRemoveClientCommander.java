package com.luxoft.bankapp.comands;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;

import com.luxoft.bankapp.dao.ClientDAO;
import com.luxoft.bankapp.dao.AccountDAO;
import com.luxoft.bankapp.handling_exceptions.DAOException;

public class DBRemoveClientCommander extends Command {


	private AccountDAO accountDAO;
	
	public AccountDAO getAccountDAO() {
		return accountDAO;
	}
	
	@Autowired
	public void setAccountDAO(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}

	public ClientDAO getClientDAO() {
		return clientDAO;
	}

	public void setClientDAO(ClientDAO clientDAO) {
		this.clientDAO = clientDAO;
	}

	private ClientDAO clientDAO;
	
	@Override
	public void execute() {

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
