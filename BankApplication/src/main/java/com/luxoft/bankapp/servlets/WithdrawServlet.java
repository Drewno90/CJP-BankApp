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
import com.luxoft.bankapp.handling_exceptions.NotEnoughFundsException;

public class WithdrawServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2986966424965780992L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
	}
	
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		ServletOutputStream out = response.getOutputStream();
		ClientDAO clientDAO = new ClientDAOImpl();
		
		String clientName = (String) request.getSession().getAttribute("clientName");
		String ammount =  request.getParameter("ammount");
		
		try {
			clientDAO.findClientByName(BankCommander.currentBank, clientName).getActiveAccount().withdraw(Float.parseFloat(ammount));;
			response.sendRedirect("/balance");
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (NotEnoughFundsException e) {
			e.printStackTrace();
		}
	}
}
