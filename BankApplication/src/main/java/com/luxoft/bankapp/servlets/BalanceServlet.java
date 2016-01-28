package com.luxoft.bankapp.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.luxoft.bankapp.comands.BankCommander;
import com.luxoft.bankapp.dao.ClientDAO;
import com.luxoft.bankapp.dao.ClientDAOImpl;
import com.luxoft.bankapp.handling_exceptions.DAOException;

public class BalanceServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7873490410163941579L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	
	}
	
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		ServletOutputStream out = response.getOutputStream();
		ClientDAO clientDAO = new ClientDAOImpl();
		String clientName = (String) request.getSession().getAttribute("clientName");
		try {
			float balance = clientDAO.findClientByName(BankCommander.currentBank, clientName).getActiveAccount().getBalance();
			out.print("Client "+clientName+" balance is: "+balance);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
}
