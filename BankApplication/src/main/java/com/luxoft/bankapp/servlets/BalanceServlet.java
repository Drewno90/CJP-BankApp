package com.luxoft.bankapp.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.luxoft.bankapp.comands.BankCommander;
import com.luxoft.bankapp.dao.AccountDAO;
import com.luxoft.bankapp.dao.AccountDAOImpl;
import com.luxoft.bankapp.dao.ClientDAO;
import com.luxoft.bankapp.dao.ClientDAOImpl;
import com.luxoft.bankapp.handling_exceptions.DAOException;
import com.luxoft.bankapp.model.Account;


public class BalanceServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7873490410163941579L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		String clientName = (String) req.getSession().getAttribute("clientName");
		ClientDAO clientDAO = new ClientDAOImpl();
		AccountDAO accountDAO = new AccountDAOImpl();
		List<Account> accountList = new ArrayList<Account>();
		
		try {
			BankCommander.currentClient = clientDAO.findClientByName(BankCommander.currentBank, clientName);
			accountList = accountDAO.getClientAccounts(BankCommander.currentClient.getId());
			for (Account account : accountList) {
				if (account.getAccountType().equals("CheckingAccount") && account.getClientId()==BankCommander.currentClient.getId()) {
					BankCommander.currentClient.setActiveAccount(account);
				}
			}
		} catch (DAOException e) {
			e.printStackTrace();
		}			


		float balance = BankCommander.currentClient.getActiveAccount().getBalance();
		
		req.setAttribute ("balance", balance);
		
		req.getRequestDispatcher("/balance.jsp").forward(req, res);
	}
	
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
	}
}
