package com.luxoft.bankapp.comands;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;

import com.luxoft.bankapp.dao.AccountDAO;
import com.luxoft.bankapp.handling_exceptions.DAOException;
import com.luxoft.bankapp.model.Account;

public class SelectActiveAccount extends Command {

	@Autowired
	private AccountDAO accountDAO;
	
	public AccountDAO getAccountDAO() {
		return accountDAO;
	}

	public void setAccountDAO(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}

	@Override
	public void execute() {

		List<Account> accountList = new ArrayList<Account>();
		try {
			accountList = accountDAO.getClientAccounts(BankCommander.currentClient.getId());

		} catch (DAOException e) {
			e.printStackTrace();
		}
		int accountNum = 0;
		for (Account account : accountList) {
			if (account.getClientId() == BankCommander.currentClient.getId()) {
				System.out.println("Type:" + account.getAccountType());
				System.out.println("account Id=" + accountNum);
			}
			accountNum++;
		}
		Scanner scan = new Scanner(System.in);
		System.out.println("Choose account (id)");
		int selectedAccount = scan.nextInt();
		bankService.setActiveAccount(BankCommander.currentClient, accountList.get(selectedAccount));
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Set active Account");

	}

}