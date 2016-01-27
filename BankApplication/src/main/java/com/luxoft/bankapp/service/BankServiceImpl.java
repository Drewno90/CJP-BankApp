package com.luxoft.bankapp.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luxoft.bankapp.comands.BankCommander;
import com.luxoft.bankapp.handling_exceptions.ClientExistsException;
import com.luxoft.bankapp.handling_exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

public class BankServiceImpl implements BankService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3663663341530119590L;

	public final static Logger LOG = LoggerFactory.getLogger(BankServiceImpl.class);

	private static final String FILE_OBJECT_DATA = "test.txt";

	@Override
	public void addClient(Bank bank, Client client) throws ClientExistsException {
		for (Client clientToCompare : bank.getClients())
			if (clientToCompare.getName().equals(client.getName()))
				throw new ClientExistsException();

		bank.addClientToClientList(client);

	}

	@Override
	public void removeClient(Bank bank, Client client) {
		bank.removeClientFromList(client);
	}

	@Override
	public void addAccount(Client client, Account account) {
		client.addAccount(account);
	}

	@Override
	public void setActiveAccount(Client client, Account account) {
		client.setActiveAccount(account);
	}

	@Override
	public Client findClientByHisName(Bank bank, String name) {
		boolean flag = false;
		if (!bank.getClients().isEmpty())
			for (Client client : bank.getClients())
				if (name.equals(client.getName())) {
					flag = true;
					return client;
				}
		if (!flag)
			LOG.warn("There is no such a Client");
		return null;
	}

	@Override
	public void getAccounts(Client client) {
		for (Account account : client.getAccounts()) {
			System.out.println(account);
		}

	}

	@Override
	public void transfer(Client clientToWhomTransfer, float ammount) {

		try {
			BankCommander.currentClient.getActiveAccount().withdraw(ammount);
		} catch (NotEnoughFundsException e) {
			LOG.warn("Not Enough Funds. Maximum of what you can transfer is {}", e.getAmount());
			e.printStackTrace();
		}
		clientToWhomTransfer.getActiveAccount().deposit(ammount);
	}

	@Override
	public void deposit(Client client, float ammount) {

		client.getActiveAccount().deposit(ammount);
	}

	@Override
	public void withdraw(Client client, float ammount) {

		try {
			client.getActiveAccount().withdraw(ammount);
		} catch (NotEnoughFundsException e) {
			LOG.warn("Not enough Funds. Maximum of what you can get is {}", e.getAmount());
			e.printStackTrace();
		}
	}

	@Override
	public Client getClient(Bank bank, String clientName) {

		return bank.getClientsMap().get(clientName);
	}

	@Override
	public void saveClient(Client client) {

		try {
			FileOutputStream fos = new FileOutputStream(FILE_OBJECT_DATA);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(client);
			oos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			LOG.error("File not found");
			e.printStackTrace();
		} catch (IOException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}

	}

	@Override
	public Client loadClient() {

		Client client = null;
		try {
			FileInputStream fis = new FileInputStream(FILE_OBJECT_DATA);
			ObjectInputStream ois = new ObjectInputStream(fis);
			client = (Client) ois.readObject();
			ois.close();
			fis.close();
		} catch (FileNotFoundException e) {
			LOG.error("File not found");
			e.printStackTrace();
		} catch (IOException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			LOG.error("No such class");
			e.printStackTrace();
		}

		return client;
	}

}
