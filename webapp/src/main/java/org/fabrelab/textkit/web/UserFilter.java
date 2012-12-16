package org.fabrelab.textkit.web;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class UserFilter implements Filter {

	private static final Logger log = Logger.getLogger(UserFilter.class.getName());
	 
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
//		try {
//			UserService userService = UserServiceFactory.getUserService();
//			HttpServletRequest request = (HttpServletRequest) req;
//			HttpServletResponse response = (HttpServletResponse) res;
//			String thisURL = request.getRequestURI();
//			if (request.getUserPrincipal() != null) {
//				chain.doFilter(request, response);
//			} else {
//				response.sendRedirect(userService.createLoginURL(thisURL));
//			}
//
//		} catch (IOException io) {
//			log.log(Level.WARNING, "IOException raised in UserFilter", io);
//		} catch (ServletException se) {
//			log.log(Level.WARNING, "ServletException raised in UserFilter", se);
//		}
		chain.doFilter(req, res);
	}


	public void destroy() {

	}

	public void init(FilterConfig arg0) throws ServletException {

	}
}
