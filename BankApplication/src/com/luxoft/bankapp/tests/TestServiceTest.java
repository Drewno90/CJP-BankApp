package com.luxoft.bankapp.tests;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.SavingAccount;

public class TestServiceTest {

    Bank bank1, bank2;

    @ Before
    public void initBanks () {

          bank1 = new Bank("My Bank");
          bank1.setId (1);
          Client client = new Client("Ivan Ivanov");
          client.setCity ("Kiev");
          Account account = new CheckingAccount (0);
          client.addAccount (account);
          Account accountS = new SavingAccount (0);
          client.addAccount (accountS);
          client.setActiveAccount(account);
          bank1.addClientToClientList(client);
          
          // Add some fields from Client
          // Marked as @ NoDB, with different values
          // For client and client2

          //Different active accounts
          bank2 = new Bank("My Bank");
          bank2.setId (2);
          Client client2 = new Client ("Ivan Ivanov");
          client2.setCity ("Kiev");
          Account account2 = new CheckingAccount (0);
          client2.addAccount (account2);
          Account account2S = new SavingAccount (0);
          client2.addAccount (account2S);
          client2.setActiveAccount(account2);
          bank2.addClientToClientList(client2);
    }

   
    @ Test
    public void testEquals()
    {
          assertTrue(TestService.isEquals(bank1, bank2));

    }

}