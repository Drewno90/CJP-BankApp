package com.luxoft.bankapp.comands;

import java.sql.SQLException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.luxoft.bankapp.dao.ClientDAO;
import com.luxoft.bankapp.handling_exceptions.DAOException;
import com.luxoft.bankapp.handling_exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.SavingAccount;

public class TransferCommand extends Command {

	private final static Logger LOG = LoggerFactory.getLogger(TransferCommand.class);


	private ClientDAO clientDAO;
	
	public ClientDAO getClientDAO() {
		return clientDAO;
	}

	@Autowired
	public void setClientDAO(ClientDAO clientDAO) {
		this.clientDAO = clientDAO;
	}
	
	@Override
	public void execute() {
		Scanner scan = new Scanner(System.in);
		System.out.println("To whom you want to send?");
		String clientName = scan.nextLine();
		
		SelectActiveAccount selectActiveAccount = new SelectActiveAccount();
		float ammount = 0;

		Client clientToWhomTransfer = bankService.findClientByHisName(BankCommander.currentBank, clientName);


		try {
			Client tempClient = BankCommander.currentClient;
			BankCommander.currentClient = clientToWhomTransfer;
			System.out.println("Choose account (id)");
			selectActiveAccount.execute();
			BankCommander.currentClient = tempClient;

			System.out.println("How much you want to transfer?");
			ammount = scan.nextFloat();

			if (BankCommander.currentClient.getActiveAccount() instanceof CheckingAccount) {
				while (BankCommander.currentClient.getActiveAccount().getBalance()
						+ BankCommander.currentClient.getInitialOverdraft() < ammount) {
					System.out.println("Not enough ammount on account. Pass another ammount");
					ammount = scan.nextFloat();
				}
				try {
					BankCommander.currentClient.getActiveAccount().withdraw(ammount);
				} catch (NotEnoughFundsException e) {
					e.printStackTrace();
				}

			} else if (BankCommander.currentClient.getActiveAccount() instanceof SavingAccount) {
				while (BankCommander.currentClient.getActiveAccount().getBalance() < ammount) {
					System.out.println("Not enough ammount on account. Pass another ammount");
					ammount = scan.nextFloat();
				}
				try {
					BankCommander.currentClient.getActiveAccount().withdraw(ammount);
				} catch (NotEnoughFundsException e) {
					e.printStackTrace();
				}
			}

			bankService.transfer(clientToWhomTransfer, ammount);

			clientDAO.save(BankCommander.currentClient);
			clientDAO.save(clientToWhomTransfer);
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		LOG.debug("{} transfered from {} to {}", ammount, BankCommander.currentClient.getName(),
				clientToWhomTransfer.getName());

	}

	@Override
	public void printCommandInfo() {
		System.out.println("Make the transfer from one client account to another client account of the same bank");

	}

}
