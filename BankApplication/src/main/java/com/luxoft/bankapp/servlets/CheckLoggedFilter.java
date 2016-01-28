package com.luxoft.bankapp.servlets;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

public class CheckLoggedFilter implements Filter{

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
		HttpSession session = ((HttpServletRequest) req).getSession();
		
		String clientName = (String) session.getAttribute("clientName");
		String path = ((HttpServletRequest) req).getRequestURI();
		HttpServletResponse response = (HttpServletResponse) resp;
		
		//if we go to login or already logged in then go on
		if(path.startsWith("/login") || path.equals("/welcome") || path.equals("/") || clientName!=null) {
			chain.doFilter(req, resp);
		}
		else {
			response.sendRedirect("/login.html");
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}


}
