package com.luxoft.bankapp.bank_application;

import com.luxoft.bankapp.handling_exceptions.ClientExistsException;
import com.luxoft.bankapp.handling_exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.handling_exceptions.OverDraftLimitExceededException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.BankReport;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.model.SavingAccount;
import com.luxoft.bankapp.service.BankServiceImpl;

public class BankApplication {

	public static Bank initialize() throws ClientExistsException
	{
		Bank bank=new Bank();
		BankServiceImpl bankService = new BankServiceImpl();
		Client client1 = new Client("Tom",500,Gender.MALE);
		bankService.addClient(bank, client1);
		Account savingAccount = new SavingAccount(300);
		Account checkingAccount = new CheckingAccount(300);
		bankService.addAccount(client1,savingAccount);
		bankService.addAccount(client1,checkingAccount);
		bankService.setActiveAccount(client1,savingAccount);
		
		Client client2 = new Client("Ben",1500,Gender.MALE);
		bankService.addClient(bank, client2);
		Account savingAccount2 = new SavingAccount(2300);
		Account checkingAccount2 = new CheckingAccount(300);
		bankService.addAccount(client2,savingAccount2);
		bankService.addAccount(client2,checkingAccount2);
		bankService.setActiveAccount(client2,savingAccount2);
		
		Client client3 = new Client("Mon",700,Gender.FEMALE);
		bankService.addClient(bank, client3);
		Account savingAccount3 = new SavingAccount(300);
		Account checkingAccount3 = new CheckingAccount(300);
		bankService.addAccount(client3,savingAccount3);
		bankService.addAccount(client3,checkingAccount3);
		bankService.setActiveAccount(client3,savingAccount3);

		
		return bank;
	}
	
	public static void modifyBank(Bank bank) throws NotEnoughFundsException
	{
		for(Client client: bank.getClients())
		client.getActiveAccount().deposit(1200);
		System.out.println("After deposit 1200 on all accounts");
		printBankReport(bank);
		
		for(Client client: bank.getClients())
		client.getActiveAccount().withdraw(255);
		
		printBankReport(bank);
	}
	
	public static void printBankReport(Bank bank)
	{
		bank.printReport();
	}
	
	public static void main(String[] args) throws ClientExistsException{
		System.out.println("Hello in bank app");
		Bank bank = initialize();
				
		printBankReport(bank);

		try {
			modifyBank(bank);
		}
		catch (OverDraftLimitExceededException e) {
			System.out.println("Overdraft Limit Exceeded. Maximum of what you can get is" + e.GetMaximumAmmountToGet());
			e.printStackTrace();
		}
		catch (NotEnoughFundsException e) {
			System.out.println("Not enough Funds. Maximum of what you can get is " + e.getAmount());
			e.printStackTrace();
		}
		
		
		BankReport bp=new BankReport();
		bp.getClientsSorted(bank);

		
	}

}



