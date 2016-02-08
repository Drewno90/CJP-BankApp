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
import com.luxoft.bankapp.model.SavingAccount;

public class WithdrawCommand extends Command {

	private final static Logger LOG = LoggerFactory.getLogger(WithdrawCommand.class);
	

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
		System.out.println("How much you want to withdraw?");
		float withdraw = scan.nextFloat();


		try {

			if (BankCommander.currentClient.getActiveAccount() instanceof CheckingAccount) {
				while (BankCommander.currentClient.getActiveAccount().getBalance()
						+ BankCommander.currentClient.getInitialOverdraft() < withdraw) {
					System.out.println("Not enough ammount on account. Pass another ammount");
					withdraw = scan.nextFloat();
				}
				BankCommander.currentClient.getActiveAccount().withdraw(withdraw);

			} else if (BankCommander.currentClient.getActiveAccount() instanceof SavingAccount) {
				while (BankCommander.currentClient.getActiveAccount().getBalance() < withdraw) {
					System.out.println("Not enough ammount on account. Pass another ammount");
					withdraw = scan.nextFloat();
				}
				BankCommander.currentClient.getActiveAccount().withdraw(withdraw);
			}
			clientDAO.save(BankCommander.currentClient);
		} catch (NotEnoughFundsException e) {
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		LOG.debug("{} withdrawed from {} account", withdraw, BankCommander.currentClient.getName());

	}

	@Override
	public void printCommandInfo() {
		System.out.println("Withdraw funds from the client account");

	}

}
