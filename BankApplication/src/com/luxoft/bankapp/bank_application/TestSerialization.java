package com.luxoft.bankapp.bank_application;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.model.SavingAccount;
import com.luxoft.bankapp.service.BankFeedService;
import com.luxoft.bankapp.service.BankFeedServiceImpl;
import com.luxoft.bankapp.service.BankServiceImpl;

public class TestSerialization {

	public static void main(String[] args) {
		
		BankFeedService bankFeedService = new BankFeedServiceImpl();
		bankFeedService.loadFeed();
		BankServiceImpl bankService = new BankServiceImpl();
		Client client1 = new Client("Tom",1600,Gender.MALE, "Warsaw");
		Account savingAccount = new SavingAccount(300);
		Account checkingAccount = new CheckingAccount(300, client1);
		bankService.addAccount(client1,savingAccount);
		bankService.addAccount(client1,checkingAccount);
		bankService.setActiveAccount(client1,checkingAccount);
	
		bankService.saveClient(client1);
		System.out.println(bankService.loadClient());
	}

}
