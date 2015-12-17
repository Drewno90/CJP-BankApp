package com.luxoft.bankapp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Client implements Report{


	
	private String name;
	private List<Account> accounts = new ArrayList<Account>();
	private Account activeAccount;
	private float initialOverdraft;
	private String mailAddress;
	private String phoneNumber;
	
	private Gender clientGender;
	
	public Client(String name,Gender clientGender)
	{
		this.name=name;
		this.setClientGender(clientGender);
		this.initialOverdraft=0;
	}
	
	public Client(String name, float initialOverdraft ,Gender clientGender)
	{
		this.name=name;
		this.setClientGender(clientGender);
		this.initialOverdraft=initialOverdraft;
	}
	
	
	public Client(String name, String mailAddress, String phoneNumber, float initialOverdraft ,Gender clientGender)
	{
		this.name=name;
		this.setClientGender(clientGender);
		this.initialOverdraft=initialOverdraft;
		this.mailAddress=mailAddress;
		this.phoneNumber=phoneNumber;
	}
	
	public void setActiveAccount(Account account){
		this.activeAccount=account;
	}
	
	public List<Account> getAccounts(){
		return Collections.unmodifiableList(accounts);
	}
	
	@Override
	public void printReport() {
		System.out.print("Name: ");
		getClientSalutation(this.clientGender);
		System.out.print(this.name);
		System.out.println("Active account: " + this.activeAccount + " Its balance "+ this.activeAccount.getBalance());
		System.out.println("List of all client accounts:");
		for(Account account: accounts)
			System.out.println(account);
	}
	
	public float getBalance(){
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
	
	public void addAccount(Account account){
		this.accounts.add(account);
	}
	
	public void getClientSalutation(Gender clientGender){
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
		return new StringBuffer().append("Client name=").append(name).append(", accounts=").append(accounts).append(", activeAccount=").append(activeAccount).append(", initialOverdraft=").append(initialOverdraft).append(", clientGender=").append(clientGender).toString();
	
	}
	
	
}



