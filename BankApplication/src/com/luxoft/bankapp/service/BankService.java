package com.luxoft.bankapp.service;

import java.io.Serializable;

import com.luxoft.bankapp.handling_exceptions.ClientExistsException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

public interface BankService extends Serializable{

    public void addClient(Bank bank,Client client) throws ClientExistsException;
    public void removeClient(Bank bank,Client client);
    public void addAccount(Client client, Account account);
    public void setActiveAccount(Client client, Account account);
	public Client findClientByHisName(Bank bank, String name);
	public void getAccounts(Client client);
	public void transfer(Client clientFromWhomTransfer, Client clientToWhomTransfer, float ammount);
	public void deposit(Client client, float ammount);
	public void withdraw(Client client, float ammount);
	public Client getClient(Bank bank, String clientName);
	public void saveClient(Client client);
	public Client loadClient();

}




