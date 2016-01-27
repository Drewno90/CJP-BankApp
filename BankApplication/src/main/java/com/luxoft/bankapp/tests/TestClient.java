package com.luxoft.bankapp.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.luxoft.bankapp.handling_exceptions.FeedException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Gender;

public class TestClient {

	private Client client1 = new Client("Tom", 1600, Gender.MALE, "Warsaw");

	@Test
	public void addAccountToClientByType() {
		Account acc = client1.createAccount("SavingAccount");
		Account acc2 = client1.createAccount("CheckingAccount");
		assertEquals("SavingAccount", acc.getAccountType());
		assertEquals("CheckingAccount", acc2.getAccountType());
	}

	@Test(expected = FeedException.class)
	public void addAccountWithBadAccountType() {
		client1.createAccount("BadAccount");
	}
}
