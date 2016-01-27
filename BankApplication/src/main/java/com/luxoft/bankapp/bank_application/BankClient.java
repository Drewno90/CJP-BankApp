package com.luxoft.bankapp.bank_application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.luxoft.bankapp.comands.BankCommander;
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

	static Logger logger = Logger.getLogger(BankClient.class.getName());

	Socket requestSocket;
	ObjectOutputStream out;
	ObjectInputStream in;
	public static String message = "";
	static String clientName;
	BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
	static final String SERVER = "localhost";
	BankServiceImpl bankService = new BankServiceImpl();

	public void run() {
		Calendar timer = Calendar.getInstance();
		try {
			logger.setLevel(Level.ALL);
			Handler handler = new ConsoleHandler();
			logger.addHandler(handler);
			// 1. creating a socket to connect to the server
			requestSocket = new Socket(SERVER, 9090);
			System.out.println("Connected to localhost in port 8080");

			logger.info("Client logged at " + timer.getTime());
			// 2. get Input and Output streams
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());

			// 3: Communicating with the server
			do {
				try {
					message = (String) in.readObject();
					System.out.println("server>> " + message);

					sendRequest(makeRequest());
				} catch (ClassNotFoundException e) {
					logger.severe("Class not found exception during receving/sending message");
					e.printStackTrace();
				}
			} while (!message.equals("Logged out"));
		} catch (UnknownHostException unknownHost) {
			logger.severe("You are trying to connect to an unknown host!");
			System.err.println("You are trying to connect to an unknown host!");

		} catch (IOException ioException) {

			ioException.printStackTrace();

		} finally {
			// 4: Closing connection
			try {
				in.close();
				out.close();
				requestSocket.close();
				long timeOfClientConnection = timer.getTimeInMillis();
				timer = Calendar.getInstance();
				timeOfClientConnection = timer.getTimeInMillis() - timeOfClientConnection;
				logger.info("Client disconnected. Time spent at work= " + timeOfClientConnection);
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
			logger.severe("IOException during sending message");
			ioException.printStackTrace();
		}
	}

	void sendRequest(Request request) {

		try {
			out.writeObject(request);
			out.flush();
			request.printInfo();
		} catch (IOException ioException) {
			logger.severe("IOException during sending request");
			ioException.printStackTrace();
		}
	}

	private BankServer selectBankService(String selection) {
		if (selection.equals("1"))
			return new BankServer();
		else if (selection.equals("2"))
			return new BankRemoteOffice();
		else
			return null;
	}

	private Client addNewClient() {
		String clientName = null;
		String gender = null;
		String city = null;
		float initialOverdraft = 0;
		try {
			System.out.println("Pass client name");
			clientName = bufferedReader.readLine();
			System.out.println("Pass client initial overdraft");
			initialOverdraft = Float.parseFloat(bufferedReader.readLine());
			System.out.println("Pass client male (MALE/FEMALE)");
			gender = bufferedReader.readLine();
			System.out.println("Pass client city");
			city = bufferedReader.readLine();
		} catch (IOException e) {
			logger.severe("IO exception in function addNewClient");
			e.printStackTrace();
		}
		if (gender.equals("MALE"))
			return new Client(clientName, initialOverdraft, Gender.MALE, city);
		else if (gender.equals("FEMALE"))
			return new Client(clientName, initialOverdraft, Gender.MALE, city);
		else {
			logger.warning("No such gender type");
			System.out.println("No such gender type.");
			addNewClient();
		}
		return null;

	}

	private Request selectRequest(String selection, BankServer bankServer) {
		if (bankServer instanceof BankRemoteOffice) {
			if (selection.equals("1"))
				return new BankStatisticsRequest();
			else if (selection.equals("2")) {
				System.out.println("Pass client name to find");
				String clientName = null;
				try {
					clientName = bufferedReader.readLine();
				} catch (IOException e) {
					logger.severe("IO exception in function selectRequest");
				}
				return new ClientInfoRequest(clientName, bankService);
			} else if (selection.equals("3")) {
				Client client = addNewClient();
				return new ClientAddRequest(client);
			} else if (selection.equals("4")) {
				System.out.println("Pass client name to remove");
				String clientName = null;
				try {
					clientName = bufferedReader.readLine();
				} catch (IOException e) {
					logger.severe("IO exception in function selectRequest");
				}
				return new ClientRemoveRequest(clientName, bankService);
			} else if (selection.equals("5")) {
				return new LogOutRequest();
			} else {
				logger.warning("There is no operation with such number!");
				System.out.println("There is no operation with such number!");
				return null;
			}
		} else {
			if (selection.equals("1"))
				return new BalanceRequest(clientName, bankService);
			else if (selection.equals("2")) {
				float ammount = 0;
				try {
					System.out.println("How much do you want to withdraw?");
					String cash = bufferedReader.readLine();
					ammount = Float.parseFloat(cash);
				} catch (NumberFormatException e) {
					logger.severe("Bad number format in function selectRequest");
					e.printStackTrace();
				} catch (IOException e) {
					logger.severe("IO exception in function selectRequest");

				}
				return new WithdrawRequest(ammount, bankService, clientName);
			} else if (selection.equals("3")) {
				return new LogOutRequest();
			} else {
				System.out.println("There is no operation with such number!");
				return null;
			}
		}
	}

	private String verifyClient(String name) throws ClassNotFoundException, IOException {

		while (bankService.findClientByHisName(BankCommander.currentBank, name) == null) {
			System.out.println("There is no such a client. Please try again or press exit.");
			name = bufferedReader.readLine();
			if (name.equals("exit"))
				System.exit(0);
		}
		return name;
	}

	private Request makeRequest() throws IOException {

		if (clientName == null) {
			try {
				System.out.println("Enter your name: ");
				clientName = bufferedReader.readLine();
				clientName = verifyClient(clientName);
				return new LogInRequest(clientName);
			} catch (IOException e) {
				logger.severe("IO exception in function make Request");
			} catch (ClassNotFoundException e) {
				logger.severe("Class not found exception in function make Request");

			}
		} else {
			BankServer bankServer = null;
			System.out.println("Select bank Service: \n 1.Bank \n 2.Bank Remote Office ");
			String selection = bufferedReader.readLine();

			bankServer = selectBankService(selection);
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
