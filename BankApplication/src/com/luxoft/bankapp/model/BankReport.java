package com.luxoft.bankapp.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
public void getBankCreditSum(Bank bank)
{
	float credit=0;
	for(Client client:bank.getClients())
		for(Account account: client.getAccounts())
				if(account.getBalance()<0)
					credit+=account.getBalance();
	System.out.println("Bank credit sum: " + -credit);
}


public void getClientsByCity(Bank bank){
	Map<String, List<Client>> clientCityMap=new TreeMap<String, List<Client>>();
	
		for(Client client:bank.getClients())
		{
			if(clientCityMap.get(client.getCity()) == null)
				{
					clientCityMap.put(client.getCity(), new LinkedList<Client>());
					clientCityMap.get(client.getCity()).add(client);
 				}
			else
				clientCityMap.get(client.getCity()).add(client);
		}
		for(String s: clientCityMap.keySet())
	        System.out.println(s + " " + clientCityMap.get(s));
		
	}


}
