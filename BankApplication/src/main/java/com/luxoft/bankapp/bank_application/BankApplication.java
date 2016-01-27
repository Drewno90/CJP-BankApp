package com.luxoft.bankapp.bank_application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	private final static Logger LOG = LoggerFactory.getLogger(BankApplication.class);

	public static Bank initialize() throws ClientExistsException {
		Bank bank = new Bank();
		BankServiceImpl bankService = new BankServiceImpl();
		Client client1 = new Client("Tom", 1600, Gender.MALE, "Warsaw");
		bankService.addClient(bank, client1);
		LOG.debug("Client {} added", client1.getName());
		Account savingAccount = new SavingAccount(1000);
		Account checkingAccount = new CheckingAccount(1000, client1);
		bankService.addAccount(client1, savingAccount);
		bankService.addAccount(client1, checkingAccount);
		bankService.setActiveAccount(client1, checkingAccount);
		LOG.debug("{} set as active account to client {}", checkingAccount.getAccountType(), client1.getName());

		Client client2 = new Client("Ben", 1700, Gender.MALE, "Krakow");
		bankService.addClient(bank, client2);
		LOG.debug("Client {} added", client2.getName());
		Account savingAccount2 = new SavingAccount(2300);
		Account checkingAccount2 = new CheckingAccount(300, client2);
		bankService.addAccount(client2, savingAccount2);
		bankService.addAccount(client2, checkingAccount2);
		bankService.setActiveAccount(client2, checkingAccount2);
		LOG.debug("{} set as active account to client {}", checkingAccount2.getAccountType(), client2.getName());

		Client client3 = new Client("Mon", 1700, Gender.FEMALE, "Poznan");
		bankService.addClient(bank, client3);
		LOG.debug("Client {} added", client3.getName());
		Account savingAccount3 = new SavingAccount(300);
		Account checkingAccount3 = new CheckingAccount(300, client3);
		bankService.addAccount(client3, savingAccount3);
		bankService.addAccount(client3, checkingAccount3);
		bankService.setActiveAccount(client3, checkingAccount3);
		LOG.debug("{} set as active account to client {}", checkingAccount3.getAccountType(), client3.getName());

		return bank;
	}

	public static void modifyBank(Bank bank) throws NotEnoughFundsException {
		for (Client client : bank.getClients())
			client.getActiveAccount().deposit(1200);
		// System.out.println("After deposit 1200 on all accounts");
		// printBankReport(bank);

		for (Client client : bank.getClients())
			client.getActiveAccount().withdraw(1900);

		// printBankReport(bank);
	}

	public static void printBankReport(Bank bank) {
		BankReport bp = new BankReport();
		bp.getClientsSorted(bank);
		bp.getBankCreditSum(bank);
		bp.getNumberOfClients(bank);
		bp.getAccountsNumber(bank);
		bp.getClientsByCity(bank);
		// bank.printReport(); // BankReport before implement class BankReport
	}

	public static void main(String[] args) throws ClientExistsException {
		System.out.println("Hello in bank app");
		Bank bank = BankApplication.initialize();

		try {
			modifyBank(bank);
		} catch (OverDraftLimitExceededException e) {
			LOG.warn("Overdraft Limit Exceeded. Maximum of what you can get is {}", e.GetMaximumAmmountToGet());
			e.printStackTrace();
		} catch (NotEnoughFundsException e) {
			LOG.warn("Not enough Funds. Maximum of what you can get is {}", e.getAmount());
			e.printStackTrace();
		}

		if (args[1].equals("report"))
			printBankReport(bank);

	}

}
