package com.luxoft.bankapp.bank_application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.requests.LogOutRequest;
import com.luxoft.bankapp.requests.Request;
import com.luxoft.bankapp.service.BankServiceImpl;

public class ServerThread implements Runnable {

    private Socket connection = null;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Bank bank;
    public static BankServiceImpl bankService = new BankServiceImpl();
  
    public ServerThread(Socket clientSocket, Bank bank)
    {
    	this.connection=clientSocket;
    	this.bank=bank;
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

                
                // 4. The two parts communicate via the input and output streams
                Request request = null;
                do {

                      try {

                    	   request= (Request) in.readObject();
                    	  	System.out.println(request);
      						request.printInfo();
      						sendMessage(request.execute());
                      } catch (ClassNotFoundException classnot) {
                            System.err.println("Data received in unknown format");
                      }
                } while (!(request instanceof LogOutRequest));
          } catch (IOException ioException) {
                ioException.printStackTrace();
          } finally {

                // 4: Closing connection
                try {
                      in.close();
                      out.close();
                     connection.close();
                      
                } catch (IOException ioException) {
                      ioException.printStackTrace();
                }
                BankServerThreaded.decrementCount();
          }
    }


 private  void sendMessage(final String msg) {

          try {
                out.writeObject(msg);
                out.flush();
                System.out.println("ser>" + " " + msg);
          } catch (IOException ioException) {
                ioException.printStackTrace();
          }
    }

  
	
    public static void displayMenu()
    {
		System.out.println("Choose number of operation to perform: \n 1.Check balance \n 2.Withdraw \n 3.Log out");	
    }
    
}
	

