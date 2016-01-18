package com.luxoft.bankapp.bank_application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.luxoft.bankapp.comands.BankCommander;
import com.luxoft.bankapp.handling_exceptions.ClientExistsException;
import com.luxoft.bankapp.requests.Request;
 
import com.luxoft.bankapp.service.BankServiceImpl;

public class BankServer {

    ServerSocket providerSocket;
    Socket connection = null;
    static ObjectOutputStream out;
    public static ObjectInputStream in;
    BufferedReader reader;
    
    BankApplication bankApplication = new BankApplication();
    public static BankServiceImpl bankService = new BankServiceImpl();
  
    
    void run() {
          try {

                // 1. creating a server socket
                providerSocket = new ServerSocket(9090);

                // 2. Wait for connection

                System.out.println("Waiting for connection");
                connection = providerSocket.accept();
                System.out.println("Connection received from "
                            + connection.getInetAddress().getHostName());
                // 3. get Input and Output streams
                out = new ObjectOutputStream(connection.getOutputStream());
                out.flush();
                in = new ObjectInputStream(connection.getInputStream());
                sendMessage("Connection successful");
                
                
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
                      providerSocket.close();
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

   
    public void displayMenu()
    {
		System.out.println("Choose number of operation to perform: \n 1.Check balance \n 2.Withdraw \n 3.Log out");	
    }
    
    public static void main(final String args[]) {

    	try {
    		BankCommander.currentBank=BankApplication.initialize();
    	} catch (ClientExistsException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
          BankServer server = new BankServer();
          while (true) {
                server.run();
          }
    }
}

