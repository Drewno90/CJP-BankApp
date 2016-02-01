package com.luxoft.bankapp.servlets;

import java.io.IOException;
import java.sql.SQLException;
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
import com.luxoft.bankapp.model.Gender;

public class SaveClientServlet extends HttpServlet{



	/**
	 * 
	 */
	private static final long serialVersionUID = 3257561654643894653L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	}
	
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {

		ClientDAO clientDAO = new ClientDAOImpl();
		Set<Client> clients = null;
		AccountDAO accountDAO = new AccountDAOImpl();
		List<Account> accountList = new ArrayList<Account>();
		Client selectedClient = null;

		int clientId =  Integer.parseInt(request.getParameter("id"));
		String clientName = request.getParameter("name");
		String clientCity = request.getParameter("city");
		String clientEmail = request.getParameter("email");
		String clientGender = request.getParameter("gender");
		float clientBalance = Float.parseFloat( request.getParameter("balance"));
		try {
			clients = clientDAO.getAllClients(BankCommander.currentBank);
			for(Client client: clients)
				if(client.getId()==clientId)
					selectedClient=client;

			accountList = accountDAO.getClientAccounts(selectedClient.getId());
			for (Account account : accountList) {
				if (account.getAccountType().equals("CheckingAccount") && account.getClientId()==selectedClient.getId()) {
					account.setBalance(clientBalance);
					selectedClient.setActiveAccount(account);
				}
			}	
		} catch (DAOException e) {
			e.printStackTrace();
		}
		selectedClient.setName(clientName);
		selectedClient.setCity(clientCity);
		selectedClient.setMailAddress(clientEmail);
		if(clientGender.equals("MALE"))
			selectedClient.setClientGender(Gender.MALE);
		else if(clientGender.equals("FEMALE"))
			selectedClient.setClientGender(Gender.FEMALE);
		
		try {
			clientDAO.save(selectedClient);
			response.sendRedirect("/clients");
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
