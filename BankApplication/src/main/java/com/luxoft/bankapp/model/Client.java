package com.luxoft.bankapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luxoft.bankapp.annotations.NoDB;
import com.luxoft.bankapp.handling_exceptions.FeedException;

public class Client implements Report, Comparable<Client>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4492568206812651133L;

	private final static Logger LOG = LoggerFactory.getLogger(Client.class);

	private String name;
	private List<Account> accounts = new ArrayList<Account>();
	@NoDB
	private Account activeAccount;
	private float initialOverdraft;
	private String mailAddress;
	private String phoneNumber;
	private String city;
	private int id;
	private Gender clientGender;
	private int bankId;

	public Client(String name, Gender gender, float overdraft) {
		this.name = name;
		this.setClientGender(clientGender);
		this.initialOverdraft = overdraft;
	}

	public Client(String name, float initialOverdraft, Gender clientGender, String city) {
		this.name = name;
		this.setClientGender(clientGender);
		this.initialOverdraft = initialOverdraft;
		this.city = city;
	}

	public Client(String name, String mailAddress, String phoneNumber, float initialOverdraft) {
		this.name = name;
		this.initialOverdraft = initialOverdraft;
		this.mailAddress = mailAddress;
		this.phoneNumber = phoneNumber;
	}

	public Client(String name, String mailAddress, String phoneNumber, float initialOverdraft, Gender clientGender,
			String city) {
		this.name = name;
		this.setClientGender(clientGender);
		this.initialOverdraft = initialOverdraft;
		this.mailAddress = mailAddress;
		this.phoneNumber = phoneNumber;
		this.city = city;
	}

	public Client(int id, String name, float initialOverdraft, String mailAddress, String phoneNumber, String city,
			Gender clientGender, int bankId) {
		this.id = id;
		this.name = name;
		this.initialOverdraft = initialOverdraft;
		this.mailAddress = mailAddress;
		this.phoneNumber = phoneNumber;
		this.clientGender = clientGender;
		this.city = city;
		this.bankId = bankId;
	}

	public Client(String name) {
		this.name = name;
	}

	public Client(String name, int overdraft, String mailAddress, String phoneNumber, String city, Gender gender,
			int bankId) {
		this.name = name;
		this.initialOverdraft = overdraft;
		this.mailAddress = mailAddress;
		this.phoneNumber = phoneNumber;
		this.clientGender = gender;
		this.city = city;
		this.bankId = bankId;
	}

	public void setActiveAccount(Account account) {
		this.activeAccount = account;
	}

	public List<Account> getAccounts() {
		return Collections.unmodifiableList(accounts);
	}

	@Override
	public void printReport() {
		System.out.print("Name: ");
		getClientSalutation(this.clientGender);
		System.out.print(this.name);
		System.out.println("Active account: " + this.activeAccount + " Its balance " + this.activeAccount.getBalance());
		System.out.println("List of all client accounts:");
		for (Account account : accounts)
			System.out.println(account);
	}

	public float getBalance() {
		return activeAccount.getBalance();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getInitialOverdraft() {
		return initialOverdraft;
	}

	public void setInitialOverdraft(float initialOverdraft) {
		this.initialOverdraft = initialOverdraft;
	}

	public Account getActiveAccount() {
		return activeAccount;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public void addAccount(Account account) {
		this.accounts.add(account);
	}

	public void getClientSalutation(Gender clientGender) {
		System.out.print(clientGender.getSalute());
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accounts == null) ? 0 : accounts.hashCode());
		result = prime * result + ((activeAccount == null) ? 0 : activeAccount.hashCode());
		result = prime * result + Float.floatToIntBits(initialOverdraft);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		if (accounts == null) {
			if (other.accounts != null)
				return false;
		} else if (!accounts.equals(other.accounts))
			return false;
		if (activeAccount == null) {
			if (other.activeAccount != null)
				return false;
		} else if (!activeAccount.equals(other.activeAccount))
			return false;
		if (Float.floatToIntBits(initialOverdraft) != Float.floatToIntBits(other.initialOverdraft))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public Gender getClientGender() {
		return clientGender;
	}

	public void setClientGender(Gender clientGender) {
		this.clientGender = clientGender;
	}

	@Override
	public String toString() {
		return new StringBuffer().append("Client name=").append(name).append(", accounts=").append(accounts)
				.append(", activeAccount=").append(activeAccount).append(", initialOverdraft=").append(initialOverdraft)
				.append(", clientGender=").append(clientGender).toString();

	}

	@Override
	public int compareTo(Client o) {

		return this.getName().compareTo(o.getName());
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	private Account getAccount(String accountType) {
		for (Account acc : accounts) {
			if (acc.getAccountType().equals(accountType)) {
				return acc;
			}
		}
		return createAccount(accountType);
	}

	/**
	 * This method creates account by its type
	 */
	public Account createAccount(String accountType) {
		Account acc;
		if ("SavingAccount".equals(accountType)) {
			acc = new SavingAccount(0);
		} else if ("CheckingAccount".equals(accountType)) {
			acc = new CheckingAccount(0);
		} else {
			LOG.warn("Accoount type not found {}", accountType);
			throw new FeedException("Account type not found " + accountType);
		}
		accounts.add(acc);
		return acc;
	}

	public void parseFeed(Map<String, String> feed) {
		// accounttype=c|s;balance=100;overdraft=50;name=John;gender=m|f;

		String accountType = feed.get("accounttype");
		Account acc = getAccount(accountType);

		this.name = feed.get(name);

		if (feed.get("gender").equals("m"))
			this.setClientGender(Gender.MALE);
		else
			this.setClientGender(Gender.FEMALE);

		this.initialOverdraft = Float.parseFloat(feed.get("overdraft"));

		acc.parseFeed(feed);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBankId() {
		return bankId;
	}

	public void setBankId(int bankId) {
		this.bankId = bankId;
	}
}
