package com.luxoft.bankapp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Bank implements Report{
	private List<Client> clientsList=new ArrayList<Client>();
	List<ClientRegistrationListener> listeners = new ArrayList<ClientRegistrationListener>();
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
		}

	}

	public void registryEvent(ClientRegistrationListener listener)
	{
		this.listeners.add(listener);
	}
	
	
	public Bank(String bankName, List<Client> clientsList){
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
	
	public List<Client> getClients(){
		return Collections.unmodifiableList(clientsList);
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

}



