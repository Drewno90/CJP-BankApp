package com.luxoft.bankapp.comands;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.luxoft.bankapp.dao.AccountDAO;
import com.luxoft.bankapp.handling_exceptions.DAOException;
import com.luxoft.bankapp.model.Account;

public class GetAccountCommand extends Command {


	private AccountDAO accountDAO;
	
	public AccountDAO getAccountDAO() {
		return accountDAO;
	}

	@Autowired
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
		for (Account account : accountList) {
//			if (account.getClientId() == BankCommander.currentClient.getId()) {
				System.out.println("Type:" + account.getAccountType());
				System.out.println("Account Balance=" + account.getBalance());
			//}
		}
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Get the list of the client accounts and the balance on each account");

	}

}
