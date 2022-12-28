package it.polimi.tiw.projects.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import it.polimi.tiw.projects.dao.UserDAO;
import it.polimi.tiw.projects.utils.ConnectionHandler;


@WebServlet("/CheckSignUp")
@MultipartConfig
public class CheckSignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;
	

	public CheckSignUp() {
		super();
	}

	public void init() throws ServletException {
		connection = ConnectionHandler.getConnection(getServletContext());
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// obtain and escape params
		String usrn = null;
		String pwd = null;
		String name= null;
		String surname=null;
		String repeatPassword=null;
		String email=null;
		
		//Get all the parameters from the request
		try {
			usrn = StringEscapeUtils.escapeJava(request.getParameter("username"));
			pwd = StringEscapeUtils.escapeJava(request.getParameter("pass"));
			name= StringEscapeUtils.escapeJava(request.getParameter("name"));
			surname= StringEscapeUtils.escapeJava(request.getParameter("surname"));
			repeatPassword= StringEscapeUtils.escapeJava(request.getParameter("repeatPwd"));
			email= StringEscapeUtils.escapeJava(request.getParameter("email"));
			
			if (usrn == null || pwd == null || usrn.isEmpty() || pwd.isEmpty() || repeatPassword.isEmpty() || email==null || repeatPassword==null || email.isEmpty()) {
				throw new Exception("Missing or empty credential value");
			}

		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("Missing credential value");
			return;
		}

		//Query to the db to check if the user is present, if not check all the parameters, if they are okay insert a new user
		UserDAO userDao = new UserDAO(connection);
		Boolean exists = null;
		try {
			exists = userDao.checkAlreadyExists(usrn);
		} catch (SQLException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().println("Not Possible to check credentials");
			return;
		}

		
		if (exists) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().println("The username already exists");
		}else if(email.toLowerCase().matches("\\b[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b")){
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().println("E-mail format is wrong");
		} 
		//Check if the password and repeat password field match
		else if(!pwd.equals(repeatPassword)){
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().println("Password do not match");
		}
		//Check if the length is at least 6
		else if(pwd.length()<6) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().println("Password lenght has to be at least 6");
		}
		else {
			try {
				userDao.addNewUser(usrn, pwd, name, surname,email);
			} catch (SQLException e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.getWriter().println("Not Possible to add a new user");
				return;
			}
			finally {
				response.setStatus(HttpServletResponse.SC_OK);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
			}

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