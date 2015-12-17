package com.luxoft.bankapp.service;


import com.luxoft.bankapp.handling_exceptions.ClientExistsException;
import com.luxoft.bankapp.handling_exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

public class BankServiceImpl implements BankService{

	@Override
	public void addClient(Bank bank, Client client) throws ClientExistsException{
		for(Client clientToCompare: bank.getClients())
			if(clientToCompare.getName().equals(client.getName()))
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
		boolean flag=false;
		for(Client client: bank.getClients())
			if(name.equals(client.getName()))
			{
				flag=true;
				return client;
			}
		if(!flag)
			System.out.println("There is no such a Client");
		return null;
	}

	@Override
	public void getAccounts(Client client) {
		for(Account account: client.getAccounts())
		{
			System.out.println(account);
		}
		
	}

	@Override
	public void transfer(Client clientFromWhomTransfer,	Client clientToWhomTransfer, float ammount) {

		try {
			clientFromWhomTransfer.getActiveAccount().withdraw(ammount);
		} catch (NotEnoughFundsException e) {
			System.out.println("Not enough Funds. Maximum of what you can transfer is " + e.getAmount());
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
			System.out.println("Not enough Funds. Maximum of what you can get is " + e.getAmount());
			e.printStackTrace();
		}
	}

}



