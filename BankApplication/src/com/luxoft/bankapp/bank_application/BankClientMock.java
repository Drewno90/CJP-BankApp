package com.luxoft.bankapp.bank_application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.concurrent.Callable;

import com.luxoft.bankapp.comands.BankCommander;
import com.luxoft.bankapp.requests.LogInRequest;
import com.luxoft.bankapp.requests.LogOutRequest;
import com.luxoft.bankapp.requests.WithdrawRequest;

public class BankClientMock extends BankClient implements Callable<Long>{

	private float ammount = 1f;

//	@Override
//	public void run() {
//		
//		try {
//			requestSocket = new Socket(SERVER, 9090);
//	         System.out.println("Connected to localhost in port 8080");
//
//	         // 2. get Input and Output streams
//	         out = new ObjectOutputStream(requestSocket.getOutputStream());
//	         out.flush();
//	         in = new ObjectInputStream(requestSocket.getInputStream());
//			
//	         message = (String) in.readObject();
//	            sendRequest(new LogInRequest(BankCommander.currentClient.getName()));
//	            message = (String) in.readObject();
//	            WithdrawRequest withdrawRequest = new WithdrawRequest(ammount, bankService,BankCommander.currentClient.getName());
//	            sendRequest(withdrawRequest);
//	            message = (String) in.readObject();
//	            sendRequest(new LogOutRequest());
//	            message = (String) in.readObject();
//		} catch (UnknownHostException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//        try {
//            in.close();
//            out.close();
//            requestSocket.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//	}

	@Override
	public Long call() throws Exception {
		
		Calendar timer = Calendar.getInstance();
		try {
			requestSocket = new Socket(SERVER, 9090);
	         System.out.println("Connected to localhost in port 8080");

	         // 2. get Input and Output streams
	         out = new ObjectOutputStream(requestSocket.getOutputStream());
	         out.flush();
	         in = new ObjectInputStream(requestSocket.getInputStream());
			
	         message = (String) in.readObject();
	            sendRequest(new LogInRequest(BankCommander.currentClient.getName()));
	            message = (String) in.readObject();
	            WithdrawRequest withdrawRequest = new WithdrawRequest(ammount, bankService,BankCommander.currentClient.getName());
	            sendRequest(withdrawRequest);
	            message = (String) in.readObject();
	            sendRequest(new LogOutRequest());
	            message = (String) in.readObject();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
        try {
            in.close();
            out.close();
            requestSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
		long timeOfClientConnection = timer.getTimeInMillis();
		timer = Calendar.getInstance();
		timeOfClientConnection = timer.getTimeInMillis() - timeOfClientConnection;
		
		return timeOfClientConnection;
	}

}

