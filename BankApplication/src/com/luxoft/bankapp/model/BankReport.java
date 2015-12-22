package com.luxoft.bankapp.model;

public class BankReport {

public void getNumberOfClients (Bank bank){
	System.out.println("Bank has " + bank.getClients().size() + " clients.");
}
public void getAccountsNumber(Bank bank){
	int accountsNumber=0;
	for(Client client:bank.getClients())
		accountsNumber+=client.getAccounts().size();
	System.out.println("Bank has " + accountsNumber + " accounts.");
}
public void getClientsSorted(Bank bank){
	
	for(Client client: bank.getClients())
		System.out.println(client.getName());
}
//getBankCreditSum(bank)
public void getClientsByCity(Bank bank){
	
}
}
