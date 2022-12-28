package it.polimi.tiw.projects.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
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

@WebServlet("/NextPage")
public class NextPage  extends HttpServlet {

	
		private static final long serialVersionUID = 1L;

		private Connection connection = null;
		
		private TemplateEngine templateEngine;

		

		public void init() throws ServletException {
			connection = ConnectionHandler.getConnection(getServletContext());
			ServletContext servletContext = getServletContext();
			ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
			templateResolver.setTemplateMode(TemplateMode.HTML);
			this.templateEngine = new TemplateEngine();
			this.templateEngine.setTemplateResolver(templateResolver);
			templateResolver.setSuffix(".html");
		}
		public NextPage() {
			super();
		}

		
		

		protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			// If the user is not logged in (not present in session) redirect to the login
			HttpSession session = request.getSession();
			if (session.isNew() || session.getAttribute("user") == null) {
				String loginpath = getServletContext().getContextPath() + "/index.html";
				response.sendRedirect(loginpath);
				return;
			}
			
			Integer currentIndex= null;
			Boolean prev=true;
			Boolean next=null;
			Integer idImage=null;
			Integer idAlbum=null;
			Image image=null;
			Album album=null;
			List<Image> imagesToSend= new LinkedList<>();
			List<Image> images=new ArrayList<>();
			AlbumDAO albumDAO=new AlbumDAO(connection);
			ImageDAO imageDAO= new ImageDAO(connection);
			
			try {
			idImage =Integer.parseInt(request.getParameter("idImage"));
			idAlbum=Integer.parseInt(request.getParameter("idAlbum"));
			}catch(NumberFormatException | NullPointerException ex){
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Incorrect param values");
				return;
			}
			
			try {
				image = imageDAO.getImageById(idImage);
			}catch (SQLException e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Value selected for images are incorrect");
				return;
			}
			try {
				images= imageDAO.getAllImageFromIdAlbum(idAlbum);
				
			}catch (SQLException e) {
					response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Value selected for images are incorrect");
						return;
			}	
			try {
				album=albumDAO.getAlbumFromId(Integer.parseInt(request.getParameter("idAlbum")));
				}
				catch (SQLException e) {
					response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Value selected for album are incorrect");
						return;
		 	}
			
			//Calculate the index of the image
			for(int i=0;i<images.size();i++) {
				if(images.get(i).getId()==image.getId())
				{
					currentIndex=i+1;
					i=images.size();
				}
				
			}
			
			//Check if the index is correct and a
			if(currentIndex==null) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Image not present in this album");
				return;
			}
			
			if(currentIndex%5!=0) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Index for the image is incorrect");
				return;
			}
				//System.out.println(currentIndex);
				//System.out.println((images.size()-currentIndex)<6);
			
			//Add the image to send	
			if((images.size()-currentIndex)<=5) {
				next=false;
				for(int i=currentIndex; i<images.size();i++)
						imagesToSend.add(images.get(i));
			}else {
				next=true;
			for(int i=currentIndex; i<currentIndex+5;i++)
				imagesToSend.add(images.get(i));
			}
			
			//Reload the page with the new images
			String path = "/WEB-INF/ShowAlbum.html";
			ServletContext servletContext = getServletContext();
			final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
			ctx.setVariable("next", next);
			ctx.setVariable("images", imagesToSend);
			ctx.setVariable("album", album);
			ctx.setVariable("prev", prev);
			templateEngine.process(path, ctx, response.getWriter());
		

		
		}
		
		protected void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			doGet( request,  response);
		
		}

		public void destroy() {
			try {
				ConnectionHandler.closeConnection(connection);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

