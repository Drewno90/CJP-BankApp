package com.luxoft.bankapp.model;

import java.io.Serializable;

import com.luxoft.bankapp.dao.AccountDAO;
import com.luxoft.bankapp.dao.AccountDAOImpl;
import com.luxoft.bankapp.dao.BankDAO;
import com.luxoft.bankapp.dao.BankDAOImpl;
import com.luxoft.bankapp.dao.ClientDAO;
import com.luxoft.bankapp.dao.ClientDAOImpl;
import com.luxoft.bankapp.handling_exceptions.BankNotFoundException;
import com.luxoft.bankapp.handling_exceptions.DAOException;

public class BankInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1478061135815033619L;
	private Bank bank;

	public BankInfo(Bank bank) {
		this.bank = bank;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public String printReport() {

		BankDAO bankDAO = new BankDAOImpl();
		ClientDAO clientDAO = new ClientDAOImpl();
		AccountDAO accountDAO = new AccountDAOImpl();
		try {
			bank = bankDAO.getBankByName(bank.getBankName());
			bank.setClientsList(clientDAO.getAllClients(bank));
			for (Client client : bank.getClientsList())
				client.setAccounts(accountDAO.getClientAccounts(client.getId()));

		} catch (DAOException e) {
			e.printStackTrace();
		} catch (BankNotFoundException e) {
			e.printStackTrace();
		}
		int accountsNumber = 0;
		for (Client client : bank.getClientsList())
			accountsNumber += client.getAccounts().size();

		float credit = 0;
		for (Client client : bank.getClientsList())
			for (Account account : client.getAccounts())
				if (account.getBalance() < 0)
					credit += account.getBalance();

		return "Bank has " + bank.getClients().size() + " clients. " + " Bank has " + accountsNumber + " accounts. "
				+ "Bank credit sum: " + -credit;

	}

}
