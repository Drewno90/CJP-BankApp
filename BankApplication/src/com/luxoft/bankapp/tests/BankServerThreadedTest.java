package com.luxoft.bankapp.tests;



import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

import com.luxoft.bankapp.bank_application.BankApplication;
import com.luxoft.bankapp.bank_application.BankClientMock;
import com.luxoft.bankapp.bank_application.BankServerThreaded;
import com.luxoft.bankapp.comands.BankCommander;
import com.luxoft.bankapp.handling_exceptions.ClientExistsException;
import com.luxoft.bankapp.service.BankServiceImpl;


public class BankServerThreadedTest {
/*
	@Test
	public void testBankServer(){

		try {
			BankCommander.currentBank= BankApplication.initialize();
		} catch (ClientExistsException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	BankServiceImpl bankService = new BankServiceImpl();
	BankCommander.currentClient = bankService.findClientByHisName(BankCommander.currentBank,"Tom");

	double ammount = BankCommander.currentClient.getActiveAccount().getBalance();


	 BankServerThreaded bankServerThreaded = new BankServerThreaded(9090);
     Thread server = new Thread(bankServerThreaded);
     server.start();

     List<Thread> mocksClients = new ArrayList<>();

     for (int i = 0; i < 1000; i++) {
         mocksClients.add(new Thread(new BankClientMock()));
     }

     for (Thread thread : mocksClients) {
         thread.start();
     }

     for (Thread thread : mocksClients) {
         try {
			thread.join();
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
     }

	double ammount2 = BankCommander.currentClient.getActiveAccount().getBalance();
	System.out.println(ammount2);
	Assert.assertEquals(ammount-1000, ammount2, 0);
	}
	*/
	@Test
	public void testBankServerCallableMock(){

		try {
			BankCommander.currentBank= BankApplication.initialize();
		} catch (ClientExistsException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	BankServiceImpl bankService = new BankServiceImpl();
	BankCommander.currentClient = bankService.findClientByHisName(BankCommander.currentBank,"Tom");

	double ammount = BankCommander.currentClient.getActiveAccount().getBalance();


	 BankServerThreaded bankServerThreaded = new BankServerThreaded(9090);
     Thread server = new Thread(bankServerThreaded);
     server.start();

 	ExecutorService executor = Executors.newFixedThreadPool(1000);
     List<Future<Long>> clientList = new ArrayList <>(1000);

     for (int i = 0; i <1000; i ++) {
          Future<Long> future = executor.submit(new BankClientMock());
          clientList.add (future);
     }
     long time=0;
     for (int i = 0; i <1000; i ++) {
         try {
			time += clientList.get(i).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     }
     
     System.out.println("Average time per Mock: " +time/1000 );
     

	double ammount2 = BankCommander.currentClient.getActiveAccount().getBalance();
	System.out.println(ammount2);
	Assert.assertEquals(ammount-1000, ammount2, 0);
	}
	
}


