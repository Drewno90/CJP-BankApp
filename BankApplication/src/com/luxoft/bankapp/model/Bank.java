package com.luxoft.bankapp.model;


import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;



public class Bank implements Report{
	private Set<Client> clientsList=new TreeSet<Client>();
	private Map<String, Client> clientsMap= new TreeMap<String, Client>();


	Set<ClientRegistrationListener> listeners = new HashSet<ClientRegistrationListener>();
	private String bankName;
	
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public interface ClientRegistrationListener {
		public void onClientAdded(Client client);
	}

	public class EmailNotificationListener implements ClientRegistrationListener{

		public void onClientAdded(Client client){
			System.out.println("Notification email for client "+client.getName()  +" to be sent");
		}	
	}
	
	public class PrintClientListener implements ClientRegistrationListener{

		public void onClientAdded(Client client)
		{
			System.out.println(client.getName());
			clientsMap.put(client.getName(), client);
		}

	}

	public void registryEvent(ClientRegistrationListener listener)
	{
		this.listeners.add(listener);
	}
	
	
	public Bank(String bankName, Set<Client> clientsList){
		this.bankName=bankName;
		registryEvent(new EmailNotificationListener());
		registryEvent(new PrintClientListener());
		this.clientsList=clientsList;
	}
	
	public Bank() {
		registryEvent(new EmailNotificationListener());
		registryEvent(new PrintClientListener());
	}

	public Bank(String bankName) {
		this.bankName=bankName;
		registryEvent(new EmailNotificationListener());
		registryEvent(new PrintClientListener());
	}
	
	public Set<Client> getClients(){
		return Collections.unmodifiableSet(clientsList);
	}

	@Override
	public void printReport() {
		for(Client client: this.clientsList){
			client.printReport();
		}
	}
	
	public void addClientToClientList(Client client)
	{

		clientsList.add(client);
		
		for(ClientRegistrationListener listener: listeners)
			listener.onClientAdded(client);

	}
	
	public void removeClientFromList(Client client)
	{
		clientsList.remove(client); 
	}
	
	
	public Map<String, Client> getClientsMap() {
		return Collections.unmodifiableMap(clientsMap);
	}

	public void setClientsMap(Map<String, Client> clientsMap) {
		this.clientsMap = clientsMap;
	}

}



