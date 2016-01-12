package com.luxoft.bankapp.bank_application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.luxoft.bankapp.comands.BankCommander;
import com.luxoft.bankapp.handling_exceptions.ClientExistsException;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.requests.BalanceRequest;
import com.luxoft.bankapp.requests.BankStatisticsRequest;
import com.luxoft.bankapp.requests.ClientAddRequest;
import com.luxoft.bankapp.requests.ClientInfoRequest;
import com.luxoft.bankapp.requests.ClientRemoveRequest;
import com.luxoft.bankapp.requests.LogInRequest;
import com.luxoft.bankapp.requests.LogOutRequest;
import com.luxoft.bankapp.requests.Request;
import com.luxoft.bankapp.requests.WithdrawRequest;
import com.luxoft.bankapp.service.BankServiceImpl;

public class BankClient {
	
	private final static Logger LOG = LoggerFactory.getLogger(BankClient.class);
	
    Socket requestSocket;
    ObjectOutputStream out;
    ObjectInputStream in;
    public static String message= "";
    static String clientName;
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    static final String SERVER = "localhost";
    BankServiceImpl bankService = new BankServiceImpl();
    
    
    void run() {
          try {
                // 1. creating a socket to connect to the server
                requestSocket = new Socket(SERVER, 9090);
                System.out.println("Connected to localhost in port 8080");

                // 2. get Input and Output streams
                out = new ObjectOutputStream(requestSocket.getOutputStream());
                out.flush();
                in = new ObjectInputStream(requestSocket.getInputStream());

                
                try {
					BankCommander.currentBank = BankApplication.initialize();
				} catch (ClientExistsException e) {
					e.printStackTrace();
				}
                // 3: Communicating with the server
                do {
                	try {
						message = (String) in.readObject();
						System.out.println("server>> " + message);
	                  
	                    sendRequest(makeRequest()); 
					}
                	catch (ClassNotFoundException e) 
                	{
						e.printStackTrace();
					}
                } while (!message.equals("bye"));
          } catch (UnknownHostException unknownHost) {
        	  
                System.err.println("You are trying to connect to an unknown host!");
                
          } catch (IOException ioException) {
        	  
                ioException.printStackTrace();
                
          } finally {
                // 4: Closing connection
                try {
                      in.close();
                      out.close();
                      requestSocket.close();
                } catch (IOException ioException) {
                      ioException.printStackTrace();
                }
          }
    }

    void sendMessage(final String msg) {
          try {
                out.writeObject(msg);
                out.flush();
                System.out.println("client>" + msg);
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
    
    private BankServer selectBankService(String selection)
    {
    	if(selection.equals("1"))
    		return new BankServer();
    	else
    		return new BankRemoteOffice();
    }
    
    private Client addNewClient()
    {
		String clientName = null;
		String gender = null;
		String city = null;
		float initialOverdraft = 0;
		try {
			System.out.println("Pass client name");
			clientName = bufferedReader.readLine();
			System.out.println("Pass client initial overdraft");
			initialOverdraft =Float.parseFloat(bufferedReader.readLine());
			System.out.println("Pass client male (MALE/FEMALE)");
			gender = bufferedReader.readLine();
			System.out.println("Pass client city");
			city = bufferedReader.readLine();
		} catch (IOException e) {
			LOG.warn("IO exception in function addNewClient");
			e.printStackTrace();
		}
		if(gender.equals("MALE"))
			return new Client(clientName, initialOverdraft, Gender.MALE, city);
		else if(gender.equals("FEMALE"))
			return new Client(clientName, initialOverdraft, Gender.MALE, city);
		else
		{
			System.out.println("No such gender type.");
			addNewClient();
		}
		return null;
    
    }
    
    private Request selectRequest(String selection, BankServer bankServer)
    {
    	if(bankServer instanceof BankRemoteOffice)
    	{
        	if(selection.equals("1"))
    			return new BankStatisticsRequest(BankCommander.currentBank);
    		else if(selection.equals("2"))
    		{
    			System.out.println("Pass client name to find");
    			String clientName = null;
				try {
					clientName = bufferedReader.readLine();
				} catch (IOException e) {
					LOG.warn("IO exception in function selectRequest");
				}
    			return new ClientInfoRequest(clientName, BankCommander.currentBank);
    		}
    		else if(selection.equals("3"))
    		{
    			Client client = addNewClient();
    			return new ClientAddRequest(client, BankCommander.currentBank);
    		}
    		else if(selection.equals("4"))
    		{
    			System.out.println("Pass client name to remove");
    			String clientName = null;
				try {
					clientName = bufferedReader.readLine();
				} catch (IOException e) {
					LOG.warn("IO exception in function selectRequest");
				}
    			return new ClientRemoveRequest(clientName, BankCommander.currentBank);
    		}
    		else if(selection.equals("5"))
    		{
    			message="bye";
    			return new LogOutRequest();
    		}
    		else
    		{
    			System.out.println("There is no operation with such number!");
    			return null;
    		}
    	}
    	else
    	{
    		if(selection.equals("1"))
    			return new BalanceRequest(clientName,BankCommander.currentBank);
    		else if(selection.equals("2"))
    		{
    			float ammount=0;
				try {
					System.out.println("How much do you want to withdraw?");
					String cash = bufferedReader.readLine();
					ammount = Float.parseFloat(cash);
				} catch (NumberFormatException e) {
					LOG.warn("Bad number format in function selectRequest");
					e.printStackTrace();
				} catch (IOException e) {
					LOG.warn("IO exception in function selectRequest");
		
				}
    			return new WithdrawRequest(clientName,BankCommander.currentBank, ammount);
    		}
    		else if(selection.equals("3"))
    		{
    			message="bye";
    			return new LogOutRequest();
    		}
    		else
    		{
    			System.out.println("There is no operation with such number!");
    			return null;
    		}
    	}
    }
    
    private String verifyClient(String name) throws ClassNotFoundException, IOException
    {
    	
	  	while(bankService.findClientByHisName(BankCommander.currentBank, name)==null)
	  	{
	  		System.out.println("There is no such a client. Please try again.");
	  		name = bufferedReader.readLine();
	  		if (BankClient.message.equals("bye"))
	  			System.exit(0);;
	  	}
	  	return name;
    }
    
    private Request makeRequest() throws IOException
    {
		
    	if(clientName==null) {
			try {
				System.out.println("Enter your name: ");
				clientName = bufferedReader.readLine();
				clientName = verifyClient(clientName);
				return new LogInRequest(clientName);
			} catch (IOException e) {
				LOG.warn("IO exception in function make Request");
			} catch (ClassNotFoundException e) {
				LOG.warn("Class not found exception in function make Request");

			}
		}
    	else
    	{
    		System.out.println("Select bank Service: \n 1.Bank \n 2.Bank Remote Office ");
    		String selection = bufferedReader.readLine();
    		
    		BankServer bankServer = selectBankService(selection);
    		bankServer.displayMenu();
    		selection = bufferedReader.readLine();
    		return selectRequest(selection, bankServer);

    	}
    	
    	return null;
    }

    public static void main(final String args[]) {

          BankClient client = new BankClient();
          client.run();
    }



}