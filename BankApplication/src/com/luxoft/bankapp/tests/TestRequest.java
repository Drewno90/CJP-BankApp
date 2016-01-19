package com.luxoft.bankapp.tests;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.luxoft.bankapp.bank_application.BankApplication;
import com.luxoft.bankapp.comands.BankCommander;
import com.luxoft.bankapp.handling_exceptions.ClientExistsException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.model.SavingAccount;
import com.luxoft.bankapp.requests.BalanceRequest;
import com.luxoft.bankapp.requests.ClientAddRequest;
import com.luxoft.bankapp.requests.ClientRemoveRequest;
import com.luxoft.bankapp.requests.Request;
import com.luxoft.bankapp.requests.WithdrawRequest;
import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.BankServiceImpl;

public class TestRequest {
	

	private Request request;
	private String clientName;
	private BankService bankService;
	private Client client;
	@Before
	public void bankInitialization() throws ClientExistsException
	{
		bankService = new BankServiceImpl();
		BankCommander.currentBank = BankApplication.initialize();
		client = new Client("Benjamin",1700,Gender.MALE, "Krakow");
		bankService.addClient(BankCommander.currentBank, client);
		Account savingAccount2 = new SavingAccount(2300);
		Account checkingAccount2 = new CheckingAccount(300, client);
		bankService.addAccount(client,savingAccount2);
		bankService.addAccount(client,checkingAccount2);
		bankService.setActiveAccount(client,checkingAccount2);
		clientName=client.getName();
		

	}
	
	@Test
	public void balanceRequestTest()
	{
		request = new BalanceRequest(clientName, bankService);
		String test = request.execute();
		assertEquals("Your Balance is " + 300f, test);
	}
	
	@Test
	public void withdrawRequestTest()
	{
		double ammount = bankService.findClientByHisName(BankCommander.currentBank, clientName).getActiveAccount().getBalance();
		request = new WithdrawRequest(100f, bankService,clientName);
		request.execute();
		double ammount2 = bankService.findClientByHisName(BankCommander.currentBank, clientName).getActiveAccount().getBalance();
		assertEquals(ammount-100f, ammount2,0);
	}
	
	@Test
	public void clientAddRequestTest()
	{
		Client client2 = new Client("Benjamina",1700,Gender.MALE, "Krakow");
		request = new ClientAddRequest(client2);
		request.execute();
		assertEquals(bankService.findClientByHisName(BankCommander.currentBank, client2.getName()), client2);
	}
	
	@Test
	public void clientDeleteRequestTest()
	{
		request = new ClientRemoveRequest(client.getName(), bankService);
		request.execute();
		Set<Client> set = BankCommander.currentBank.getClients();
		Assert.assertFalse(set.contains(client));
	}
	
}
