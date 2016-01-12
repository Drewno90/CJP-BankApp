package com.luxoft.bankapp.bank_application;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BankServerThreaded implements Runnable{

	final static int POOL_SIZE=10;
	

    protected int serverPort   = 9090;
    protected ServerSocket serverSocket;
	Socket clientSocket;
	ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);
	volatile private boolean running = true;
	
	  public BankServerThreaded(int port){
	        this.serverPort = port;
	    }
	
	@Override
	public void run() {
        openServerSocket();
		while (running) {
			try {
				clientSocket = this.serverSocket.accept();
			} catch (IOException e) {

				e.printStackTrace();
			}


			        pool.execute(new ServerThread(clientSocket));

			}
	}
	

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port " + this.serverPort, e);
        }
    }
	
    public static void main(String[] args)
    {
	BankServerThreaded bst = new BankServerThreaded(9090);

	bst.run();
    }
}
