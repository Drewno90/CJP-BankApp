package com.luxoft.bankapp.tests;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import com.luxoft.bankapp.dao.BankDAOImpl;
import com.luxoft.bankapp.handling_exceptions.BankNotFoundException;
import com.luxoft.bankapp.handling_exceptions.DAOException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.SavingAccount;

public class BankDAOTest {

	Bank bank;

	@Before
	public void initBank() {
		bank = new Bank();
		bank.setBankName("My Bank");
		Client client = new Client("Ivan Ivanov");
		client.setCity("Kiev");
		client.addAccount(new CheckingAccount(0));
		bank.addClientToClientList(client);
	}

	@Test
	public void testInsert() {
		BankDAOImpl bankDao = new BankDAOImpl();
		Bank bank2 = null;
		try {
			bankDao.save(bank);
			bank2 = bankDao.getBankByName(bank.getBankName());
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (BankNotFoundException e) {
			e.printStackTrace();
		}
		assertTrue(TestService.isEquals(bank, bank2));
	}

	@Test
	public void testUpdate() {
		BankDAOImpl bankDao = new BankDAOImpl();
		Bank bank2 = null;
		try {
			bankDao.save(bank);
			Client client2 = new Client("Ivan Petrov");
			client2.setCity("New York");
			client2.addAccount(new SavingAccount(0));
			bank.addClientToClientList(client2);
			bankDao.save(bank);

			bank2 = bankDao.getBankByName(bank.getBankName());
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (BankNotFoundException e) {
			e.printStackTrace();
		}

		assertTrue(TestService.isEquals(bank, bank2));
	}

}