package com.luxoft.bankapp.servlets;

import java.io.IOException;
import java.sql.SQLException;
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
import com.luxoft.bankapp.handling_exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.model.Account;


public class WithdrawServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2986966424965780992L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
	}
	
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		//ServletOutputStream out = response.getOutputStream();
		ClientDAO clientDAO = new ClientDAOImpl();
		AccountDAO accountDAO = new AccountDAOImpl();
		List<Account> accountList = new ArrayList<Account>();

		
		String clientName = (String) request.getSession().getAttribute("clientName");
		String ammount =  request.getParameter("ammount");
		
		try {
			BankCommander.currentClient = clientDAO.findClientByName(BankCommander.currentBank, clientName);			
			accountList = accountDAO.getClientAccounts(BankCommander.currentClient.getId());
			for (Account account : accountList) {

				if (account.getAccountType().equals("CheckingAccount") && account.getClientId()==BankCommander.currentClient.getId()) {
					BankCommander.currentClient.setActiveAccount(account);
					System.out.println(account);
				}
			}
			System.out.println(BankCommander.currentClient.getActiveAccount());
			BankCommander.currentClient.getActiveAccount().withdraw(Float.parseFloat(ammount));
			clientDAO.save(BankCommander.currentClient);
			response.sendRedirect("/balance");
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (NotEnoughFundsException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
