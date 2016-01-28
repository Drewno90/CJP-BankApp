package com.luxoft.bankapp.servlets;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionsAmountServlet implements HttpSessionListener{

	@Override
	public void sessionCreated(final HttpSessionEvent httpSessionEvent) {
		final HttpSession session = httpSessionEvent.getSession();
		final ServletContext context = httpSessionEvent.getSession().getServletContext();

		System.out.println("Session created: id=" + session.getId());
		System.out.println("Ammount of connections: "+ context.getAttribute("clientsConnected"));
	}

	@Override
	public void sessionDestroyed(final HttpSessionEvent httpSessionEvent) {
		final HttpSession session = httpSessionEvent.getSession();
		final ServletContext context = httpSessionEvent.getSession().getServletContext();

		System.out.println("Session destroyed: id=" + session.getId());
		System.out.println("Ammount of connections: "+ context.getAttribute("clientsConnected"));

	}

}
