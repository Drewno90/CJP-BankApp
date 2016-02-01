package com.luxoft.bankapp.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LoginServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6763578840178806481L;
	
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	}
	
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		
		/** get lient name; raise an exception if no client name specified */
		final String clientName = request.getParameter("clientName");
		if(clientName == null) {
			//logger.warn("Client not found");
			throw new ServletException("No client specified.");
		}
		request.getSession().setAttribute("clientName", clientName);
		//logger.info("Client "+ clientName+" logged into ATM");


		/** redirect to menu */
		response.sendRedirect("/menu.html");
	}
	
}
