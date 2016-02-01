package com.luxoft.bankapp.servlets;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.luxoft.bankapp.comands.BankCommander;
import com.luxoft.bankapp.dao.BankDAO;
import com.luxoft.bankapp.dao.BankDAOImpl;
import com.luxoft.bankapp.dao.ClientDAO;
import com.luxoft.bankapp.dao.ClientDAOImpl;
import com.luxoft.bankapp.handling_exceptions.BankNotFoundException;
import com.luxoft.bankapp.handling_exceptions.DAOException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

public class WelcomeServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 704154167432374882L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		ClientDAO clientDAO = new ClientDAOImpl();
		Set<Client> bankClientsList;
		BankDAO bankDAO = new BankDAOImpl();
		
		Bank bank;
		try {
			bank = bankDAO.getBankByName("My Bank");
			bankClientsList = clientDAO.getAllClients(bank);
			BankCommander.currentBank = bank;
			BankCommander.currentBank.setClientsList(bankClientsList);
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (BankNotFoundException e) {
			e.printStackTrace();
		}

		
		res.sendRedirect("/login.html");
	}

}
