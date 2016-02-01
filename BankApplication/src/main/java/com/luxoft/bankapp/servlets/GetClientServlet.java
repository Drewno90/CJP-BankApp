package com.luxoft.bankapp.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import com.luxoft.bankapp.model.Client;

public class GetClientServlet extends HttpServlet{



	/**
	 * 
	 */
	private static final long serialVersionUID = 6792427183268770691L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		ClientDAO clientDAO = new ClientDAOImpl();
		Set<Client> clients = null;
		AccountDAO accountDAO = new AccountDAOImpl();
		List<Account> accountList = new ArrayList<Account>();
		Client selectedClient = null;
		float balance = 0;
		int clientId =  Integer.parseInt(req.getParameter("id"));

		try {
			clients = clientDAO.getAllClients(BankCommander.currentBank);
			for(Client client: clients)
				if(client.getId()==clientId)
					selectedClient=client;

			accountList = accountDAO.getClientAccounts(selectedClient.getId());
			for (Account account : accountList) {
				if (account.getAccountType().equals("CheckingAccount") && account.getClientId()==selectedClient.getId()) {
					balance = account.getBalance();
				}
			}
			
		} catch (DAOException e) {
			e.printStackTrace();
		}			
		
		req.setAttribute ("client", selectedClient);
		req.setAttribute("gender", selectedClient.getClientGender());
		req.setAttribute("email", selectedClient.getMailAddress());
		req.setAttribute("balance", balance);
		req.getRequestDispatcher("/client.jsp").forward(req, res);
		
	}
	
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
	}

}
