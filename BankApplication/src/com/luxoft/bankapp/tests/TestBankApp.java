package com.luxoft.bankapp.tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.luxoft.bankapp.bank_application.BankApplication;
import com.luxoft.bankapp.handling_exceptions.ClientExistsException;
import com.luxoft.bankapp.model.Bank;

public class TestBankApp {

	@Test
	public void bankInitialization() throws ClientExistsException{
		BankApplication bp = new BankApplication();
		Bank bank = bp.initialize();
		assertTrue(bank!=null);
	}
	
}
