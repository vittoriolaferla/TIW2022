package it.polimi.tiw.projects.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.tiw.projects.beans.Album;
import it.polimi.tiw.projects.beans.Image;
import it.polimi.tiw.projects.beans.User;
import it.polimi.tiw.projects.dao.AlbumDAO;
import it.polimi.tiw.projects.dao.ImageDAO;

import it.polimi.tiw.projects.utils.ConnectionHandler;

@WebServlet("/CreateAlbum")
public class CreateAlbum extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Connection connection = null;
	
	private TemplateEngine templateEngine;

	

	private Timestamp getDate() {
		return new Timestamp(System.currentTimeMillis());
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
	public CreateAlbum() {
		super();
	}

	
	

protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// If the user is not logged in (not present in session) redirect to the login
		HttpSession session = request.getSession();
		if (session.isNew() || session.getAttribute("user") == null) {
			String loginpath = getServletContext().getContextPath() + "/index.html";
			response.sendRedirect(loginpath);
			return;
		}
		
		User user = (User) session.getAttribute("user");
		String title = null;
		List<Integer> idImage=new LinkedList<>();
		/*
		* Get all the variables from the POST through the function getParameterMAp
		*/
		Map<String, String[]> allMap = request.getParameterMap();
		
		for (String key : allMap.keySet()) {	
			
		String[] strArr = (String[]) allMap.get(key);
			for (String val : strArr) { 
			    if(key.equals("title")) {
			    	//System.out.println(val + " was the map " + key + " was the key");
			        	title= val;
			     }
			     else if(key.equals("id")) {
			    	 try {
			        	idImage.add(Integer.parseInt(val));
			    	 }catch(NumberFormatException | NullPointerException e){ 
			    		response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Incorrect param value");
			    		return;
			    	 }
			       }
			  }	
		}
	
		try {
			if (title == null || title.isEmpty() || idImage.isEmpty() || idImage==null ) {
				throw new Exception("Missing or empty title value");
				}
			
			} catch (Exception e) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing form required value");
				return;
			}
		
		
		AlbumDAO albumDAO= new AlbumDAO(connection);
		ImageDAO imageDAO= new ImageDAO(connection);
			   
		
		
	
		//Check if for each image if it has already an album, if so throw an exception
		for(Integer imageId : idImage) {
				try {
					
					if(imageDAO.getIdAlbum(imageId)==null) {
						response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Image not found");
						return;
					}
					if(imageDAO.getIdAlbum(imageId)!=0) {
						response.sendError(HttpServletResponse.SC_BAD_REQUEST, "You selected a image with already an album");
						return;
					}
				} catch (SQLException | IOException e) {
					e.printStackTrace();
				}
		}
			
			
		//Create a new Album
		try {
			albumDAO.createAlbum(user.getId(), title, user.getName() + " " + user.getSurname(),getDate());
		} catch (SQLException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Incoorect param values for  album");
			return;
		}
		
		//For each image add them on the last album created by the user 
		for(Integer image : idImage) {
				try{
					imageDAO.addImage(image, albumDAO.getLastAlbumFromIdUser(user.getId()));
				
					} catch (SQLException e) {
							response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Incoorect param values for  album");
							return;
					}
			}
		

		// return the user to the right view
		String ctxpath = getServletContext().getContextPath();
		String path = ctxpath + "/Home";
		response.sendRedirect(path);
	}


public void destroy() {
		try {
			ConnectionHandler.closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

