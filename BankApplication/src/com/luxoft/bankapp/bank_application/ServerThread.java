package com.luxoft.bankapp.bank_application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

import com.luxoft.bankapp.requests.Request;
import com.luxoft.bankapp.service.BankServiceImpl;

public class ServerThread implements Runnable {

	private AtomicInteger counter=new AtomicInteger(0);
	
    ServerSocket providerSocket;
    Socket connection = null;
    static ObjectOutputStream out;
    public static ObjectInputStream in;
    BufferedReader reader;

    BankApplication bankApplication = new BankApplication();
    public static BankServiceImpl bankService = new BankServiceImpl();
  
    public ServerThread(Socket clientSocket)
    {
    	this.connection=clientSocket;
    }
    
    public void run() {
          try {

                // 1. creating a server socket
                // 2. Wait for connection

                System.out.println("Waiting for connection");
                System.out.println("Connection received from "
                            + connection.getInetAddress().getHostName());
                // 3. get Input and Output streams
                out = new ObjectOutputStream(connection.getOutputStream());
                out.flush();
                in = new ObjectInputStream(connection.getInputStream());
                sendMessage("Connection successful");
                incrementCount();
                
                // 4. The two parts communicate via the input and output streams

                do {

                      try {

                    	  	Request request = (Request) in.readObject();
      						request.printInfo();
      						sendMessage(request.execute());
                            if(BankClient.message.equals("bye"))
                                  sendMessage("bye");
                      } catch (ClassNotFoundException classnot) {
                            System.err.println("Data received in unknown format");
                      }
                } while (!BankClient.message.equals("bye"));
          } catch (IOException ioException) {
                ioException.printStackTrace();
          } finally {

                // 4: Closing connection
                try {
                      in.close();
                      out.close();
                      connection.close();
                      decrementCount();
                } catch (IOException ioException) {
                      ioException.printStackTrace();
                }
          }
    }


 public static void sendMessage(final String msg) {

          try {
                out.writeObject(msg);
                out.flush();
                System.out.println("ser>" + msg);
          } catch (IOException ioException) {
                ioException.printStackTrace();
          }
    }

    void sendRequest(Request request) {

        try {
              out.writeObject(request);
              out.flush();
              request.printInfo();
        } catch (IOException ioException) {
              ioException.printStackTrace();
        }
  }

    public void incrementCount()
	{
		counter.incrementAndGet();
	}
	
	public void decrementCount()
	{
		counter.decrementAndGet();
	}
	
	public int getCount()
	{
		return counter.get();
	}
	
    public void displayMenu()
    {
		System.out.println("Choose number of operation to perform: \n 1.Check balance \n 2.Withdraw \n 3.Log out");	
    }
    
}
	