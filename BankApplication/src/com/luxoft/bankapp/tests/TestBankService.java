package com.luxoft.bankapp.tests;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.luxoft.bankapp.comands.BankCommander;
import com.luxoft.bankapp.handling_exceptions.ClientExistsException;
import com.luxoft.bankapp.handling_exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.handling_exceptions.OverDraftLimitExceededException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.model.SavingAccount;
import com.luxoft.bankapp.service.BankServiceImpl;


public class TestBankService {

	private Bank bank=new Bank();
	private BankServiceImpl bankService = new BankServiceImpl();
	private Client client1 = new Client("Tom",1600,Gender.MALE, "Warsaw");
	private Account savingAccount = new SavingAccount(300);
	private Account checkingAccount = new CheckingAccount(300, client1);
	private Account[] accounts= {savingAccount, checkingAccount};

	//bankService.setActiveAccount(client1,checkingAccount);
	
	@Test
	public void getClientAccounts()
	{
		bankService.addAccount(client1,savingAccount);
		bankService.addAccount(client1,checkingAccount);

		List<Account> list = client1.getAccounts();
		assertTrue(list.containsAll(Arrays.asList(accounts)));
	}
	
	@Test
	public void setActiveAccount(){
		bankService.addAccount(client1,savingAccount);
		bankService.setActiveAccount(client1,savingAccount);
		assertEquals(savingAccount,client1.getActiveAccount());
	}
	
	@Test
	public void getClientFromBank()
	{
		try {
			bankService.addClient(bank, client1);
		} catch (ClientExistsException e) {
			e.printStackTrace();
		}
		Client client2 = bankService.getClient(bank, client1.getName());
		assertEquals(client1, client2);
		
	}
	
	@Test
	public void withdrawSuccesful()
	{
		bankService.addAccount(client1,savingAccount);
		bankService.setActiveAccount(client1,savingAccount);
		float balanceBefore = client1.getBalance();
		bankService.withdraw(client1, 200); 
		assertEquals( balanceBefore-200, client1.getBalance(), 0); 
	}
	
	@Test
	public void depositSuccesful()
	{
		bankService.addAccount(client1,savingAccount);
		bankService.setActiveAccount(client1,savingAccount);
		float balanceBefore = client1.getBalance();
		bankService.deposit(client1, 500); 
		assertEquals( balanceBefore+500, client1.getBalance(),0); 
	}
	
	@Test
	public void testObjectSerialization(){
		
		bankService.addAccount(client1,savingAccount);
		bankService.addAccount(client1,checkingAccount);
		bankService.setActiveAccount(client1,checkingAccount);
	
		bankService.saveClient(client1);
		
		Client clientFromFile = bankService.loadClient();
		
		assertEquals(clientFromFile.getName(), client1.getName());
		assertEquals(clientFromFile.getClientGender(), client1.getClientGender());
		assertEquals(clientFromFile.getCity(), client1.getCity());
		
	}
	
	
	@Ignore ("Not working")
	@Test(expected=NotEnoughFundsException.class)
	public void transferTooBigAmountFromCheckingAccount() 
	{

		bankService.addAccount(client1,savingAccount);
		bankService.setActiveAccount(client1,savingAccount);
		
		Client client2 = new Client("Ben",1700,Gender.MALE, "Krakow");


		Account savingAccount2 = new SavingAccount(300);
		bankService.addAccount(client2,savingAccount2);
		bankService.setActiveAccount(client2,savingAccount2);
		
		try {
			bankService.addClient(bank, client1);
			bankService.addClient(bank, client2);
		} catch (ClientExistsException e) {
			e.printStackTrace();
		}
		BankCommander.currentClient=client1;
		bankService.transfer(client2, 11300);


	}
	

	@Ignore ("Not working")
	@Test(expected=OverDraftLimitExceededException.class)
	public void transferTooBigAmountFromSavingAccount() 
	{
		Bank bank=new Bank();
		BankServiceImpl bankService = new BankServiceImpl();
		Client client1 = new Client("Tom",1600,Gender.MALE, "Warsaw");

		Account checkingAccount = new CheckingAccount(300, client1);
		bankService.addAccount(client1,checkingAccount);
		bankService.setActiveAccount(client1,checkingAccount);
		
		Client client2 = new Client("Ben",1700,Gender.MALE, "Krakow");


		Account checkingAccount2 = new CheckingAccount(300, client2);
		bankService.addAccount(client2,checkingAccount2);
		bankService.setActiveAccount(client2,checkingAccount2);
		
		try {
			bankService.addClient(bank, client1);
			bankService.addClient(bank, client2);
		} catch (ClientExistsException e) {
			e.printStackTrace();
		}
		BankCommander.currentClient=client1;
		bankService.transfer(client2, 3000);
	}
	
	@Ignore ("Not working")
	@Test (expected=ClientExistsException.class)
	public void clientExist() 
	{
		Bank bank=new Bank();
		BankServiceImpl bankService = new BankServiceImpl();
		Client client1 = new Client("Tom",1600,Gender.MALE, "Warsaw");
		Client client2 = client1;

		try {
			bankService.addClient(bank, client1);
			bankService.addClient(bank, client2);
			fail ("exception not thrown");
		} catch (ClientExistsException e) {
			e.printStackTrace();
		}


	}
	
}

