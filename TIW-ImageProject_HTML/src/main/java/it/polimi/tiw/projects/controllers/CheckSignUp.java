package it.polimi.tiw.projects.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.tiw.projects.dao.UserDAO;
import it.polimi.tiw.projects.utils.ConnectionHandler;


@WebServlet("/CheckSignUp")
public class CheckSignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;
	private TemplateEngine templateEngine;

	public CheckSignUp() {
		super();
	}

	public void init() throws ServletException {
		connection = ConnectionHandler.getConnection(getServletContext());
		ServletContext servletContext = getServletContext();
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
		templateResolver.setTemplateMode(TemplateMode.HTML);
		this.templateEngine = new TemplateEngine();
		this.templateEngine.setTemplateResolver(templateResolver);
		templateResolver.setSuffix(".html");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// obtain and escape params
		String usrn = null;
		String pwd = null;
		String name= null;
		String surname=null;
		String repeatPassword=null;
		
		try {
			usrn = StringEscapeUtils.escapeJava(request.getParameter("username"));
			pwd = StringEscapeUtils.escapeJava(request.getParameter("pwd"));
			name= StringEscapeUtils.escapeJava(request.getParameter("name"));
			surname= StringEscapeUtils.escapeJava(request.getParameter("surname"));
			repeatPassword= StringEscapeUtils.escapeJava(request.getParameter("repeatPwd"));
			if (usrn == null || pwd == null || usrn.isEmpty() || pwd.isEmpty() || repeatPassword.isEmpty()) {
				throw new Exception("Missing or empty credential value");
			}

		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing credential value");
			return;
		}

		// query db to authenticate for user
		UserDAO userDao = new UserDAO(connection);
		Boolean exists = null;
		try {
				exists = userDao.checkAlreadyExists(usrn);
			} catch (SQLException e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Not Possible to check credentials");
				return;
			}
	
			// If the user exists, add info to the session and go to home page, otherwise
			// show login page with error message

		String path;
		if (exists) {
			ServletContext servletContext = getServletContext();
			final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
			ctx.setVariable("errorMsgSignUp", "Username already exists");
			path = "/index.html";
			templateEngine.process(path, ctx, response.getWriter());
		} 
		
		//Check if the password and repeat password field match
		else if(!pwd.equals(repeatPassword)){
			ServletContext servletContext = getServletContext();
			final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
			ctx.setVariable("errorMsgSignUp", "Field password and repeat password do not match");
			path = "/index.html";
			templateEngine.process(path, ctx, response.getWriter());
		}
		//Check if the length is at least 6
		else if(pwd.length()<6) {
			ServletContext servletContext = getServletContext();
			final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
			ctx.setVariable("errorMsgSignUp", "Password must be at least 6 character");
			path = "/index.html";
			templateEngine.process(path, ctx, response.getWriter());
		}
		else {
		
			try {
				userDao.addNewUser(usrn, pwd, name, surname);
			} catch (SQLException e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Not Possible to add a new user");
				return;
			}
			
			ServletContext servletContext = getServletContext();
			String ctxpath = getServletContext().getContextPath();
			path = "/index.html";
			final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
			ctx.setVariable("errorMsgSignUp", "Operation successful, now you can login");
			templateEngine.process(path, ctx, response.getWriter());
		

		}
	}
	
	public void destroy() {
		try {
			ConnectionHandler.closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}