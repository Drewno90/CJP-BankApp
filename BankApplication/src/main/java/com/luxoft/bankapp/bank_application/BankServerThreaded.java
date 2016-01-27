package com.luxoft.bankapp.bank_application;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.*;

import com.luxoft.bankapp.comands.BankCommander;
import com.luxoft.bankapp.handling_exceptions.ClientExistsException;

public class BankServerThreaded implements Runnable {

	static Logger logger = Logger.getLogger(BankServerThreaded.class.getName());

	final static int POOL_SIZE = 1200;

	BankServerMonitor serverMonitor = new BankServerMonitor();
	protected int serverPort = 9090;
	protected ServerSocket serverSocket;
	ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);
	private boolean running = true;
	private static AtomicInteger counter = new AtomicInteger(0);

	public BankServerThreaded(int port) {
		this.serverPort = port;
	}

	@Override
	public void run() {
		logger.setLevel(Level.ALL);
		Handler handler = new ConsoleHandler();
		logger.addHandler(handler);

		openServerSocket();

		while (running) {
			pool.execute(serverMonitor);
			try {
				Socket clientSocket = serverSocket.accept();
				incrementCount();
				pool.execute(new ServerThread(clientSocket, BankCommander.currentBank));

			} catch (IOException e) {
				pool.shutdown();
				logger.log(Level.SEVERE, e.getMessage(), e);
				e.printStackTrace();
			}

		}
	}

	private void openServerSocket() {
		try {
			serverSocket = new ServerSocket(serverPort);
		} catch (IOException e) {
			logger.severe("Cannot open port " + this.serverPort + "- IOException");
			throw new RuntimeException("Cannot open port " + this.serverPort, e);
		}
	}

	public void incrementCount() {
		counter.incrementAndGet();
	}

	public static void decrementCount() {
		counter.decrementAndGet();
	}

	public static int getCount() {
		return counter.get();
	}

	public static void main(String[] args) {
		logger.setLevel(Level.ALL);
		Handler handler = new ConsoleHandler();
		logger.addHandler(handler);

		try {
			BankCommander.currentBank = BankApplication.initialize();
		} catch (ClientExistsException e) {
			logger.warning("Client exist exception during bank initialization");
			e.printStackTrace();
		}
		BankServerThreaded bst = new BankServerThreaded(9090);

		bst.run();
	}
}
