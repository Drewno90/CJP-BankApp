package com.luxoft.bankapp.bank_application;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import com.luxoft.bankapp.comands.BankCommander;
import com.luxoft.bankapp.handling_exceptions.ClientExistsException;


public class BankServerThreaded implements Runnable{

	final static int POOL_SIZE=1200;
	
	BankServerMonitor serverMonitor=new BankServerMonitor();
    protected int serverPort   = 9090;
    protected ServerSocket serverSocket;
	ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);
	private boolean running = true;
	private static AtomicInteger counter=new AtomicInteger(0);
	
	  public BankServerThreaded(int port){
	        this.serverPort = port;
	    }
	
	@Override
	public void run() {
        openServerSocket();

		while (running) {
	        pool.execute(serverMonitor);
			try {
				Socket clientSocket = serverSocket.accept();
                incrementCount();
		        pool.execute(new ServerThread(clientSocket, BankCommander.currentBank));
		        
			} catch (IOException e) {
				pool.shutdown();
				e.printStackTrace();
			}

			}
	}
	

    private void openServerSocket() {
        try {
            serverSocket = new ServerSocket(serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port " + this.serverPort, e);
        }
    }
	
    public void incrementCount()
  	{
  		counter.incrementAndGet();
  	}
  	
  	public static void decrementCount()
  	{
  		counter.decrementAndGet();
  	}
  	
  	public static int getCount()
  	{
  		return counter.get();
  	}
    
    public static void main(String[] args)
    {
    try {
		BankCommander.currentBank=BankApplication.initialize();
	} catch (ClientExistsException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	BankServerThreaded bst = new BankServerThreaded(9090);

	bst.run();
    }
}

