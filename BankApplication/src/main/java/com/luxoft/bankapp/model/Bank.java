package com.luxoft.bankapp.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luxoft.bankapp.annotations.NoDB;

public class Bank implements Report, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4067874232984875533L;

	private final static Logger LOG = LoggerFactory.getLogger(Bank.class);

	@NoDB
	private Map<String, Client> clientsMap = new TreeMap<String, Client>();
	private String bankName;
	private int id;
	@NoDB
	private Set<ClientRegistrationListener> listeners = new HashSet<ClientRegistrationListener>();
	private Set<Client> clientsList = new TreeSet<Client>();

	public Set<Client> getClientsList() {
		return clientsList;
	}

	public void setClientsList(Set<Client> clientsList) {
		this.clientsList = clientsList;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public interface ClientRegistrationListener extends Serializable {
		public void onClientAdded(Client client);
	}

	public class EmailNotificationListener implements ClientRegistrationListener {

		/**
		 * 
		 */
		private static final long serialVersionUID = 7278733256810148823L;

		public void onClientAdded(Client client) {
			LOG.debug("Notification email for client {} to be sent", client.getName());
		}
	}

	@Override
	public String toString() {
		return new StringBuffer().append("Bank name=").append(bankName).toString();

	}

	public class PrintClientListener implements ClientRegistrationListener {

		/**
		 * 
		 */
		private static final long serialVersionUID = -2309265246571143610L;

		public void onClientAdded(Client client) {
			System.out.println(client.getName());
			clientsMap.put(client.getName(), client);
		}

	}

	public void registryEvent(ClientRegistrationListener listener) {
		this.listeners.add(listener);
	}

	public Bank(String bankName, Set<Client> clientsList) {
		this.bankName = bankName;
		registryEvent(new EmailNotificationListener());
		registryEvent(new PrintClientListener());
		this.clientsList = clientsList;
	}

	public Bank() {
		registryEvent(new EmailNotificationListener());
		registryEvent(new PrintClientListener());
	}

	public Bank(String bankName) {
		this.bankName = bankName;
		registryEvent(new EmailNotificationListener());
		registryEvent(new PrintClientListener());
	}

	public Set<Client> getClients() {
		return Collections.unmodifiableSet(clientsList);
	}

	@Override
	public void printReport() {
		for (Client client : this.clientsList) {
			client.printReport();
		}
	}

	public void addClientToClientList(Client client) {

		clientsList.add(client);

		for (ClientRegistrationListener listener : listeners)
			listener.onClientAdded(client);

	}

	public void removeClientFromList(Client client) {
		clientsList.remove(client);
	}

	public Map<String, Client> getClientsMap() {
		return Collections.unmodifiableMap(clientsMap);
	}

	public void setClientsMap(Map<String, Client> clientsMap) {
		this.clientsMap = clientsMap;
	}

	public void parseFeed(Map<String, String> feed) {
		String name = feed.get("name");
		Client client = null;

		if (feed.get("gender").equals("m")) {
			client = new Client(name, Gender.MALE, Float.parseFloat(feed.get("overdraft")));
		} else if (feed.get("gender").equals("f")) {
			client = new Client(name, Gender.FEMALE, Float.parseFloat(feed.get("overdraft")));
		}

		if (clientsMap.get(name) == null) {
			client = new Client(name);
			clientsMap.put(name, client);
		}

		client.parseFeed(feed);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
