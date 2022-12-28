package it.polimi.tiw.projects.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
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

import org.apache.commons.lang.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.tiw.projects.beans.Album;
import it.polimi.tiw.projects.beans.Comment;
import it.polimi.tiw.projects.beans.Image;
import it.polimi.tiw.projects.beans.User;
import it.polimi.tiw.projects.dao.AlbumDAO;
import it.polimi.tiw.projects.dao.CommentDAO;
import it.polimi.tiw.projects.dao.ImageDAO;
import it.polimi.tiw.projects.utils.ConnectionHandler;


@WebServlet("/CreateComment")
public class CreateComment extends HttpServlet {
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
		
			public CreateComment() {
				super();
			}

			
			

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String path;
				// If the user is not logged in (not present in session) redirect to the login
		HttpSession session = request.getSession();
		if (session.isNew() || session.getAttribute("user") == null) {
				String loginpath = getServletContext().getContextPath() + "/index.html";
				response.sendRedirect(loginpath);
				return;
			}
				
				
				// Get and parse all parameters from request
			User user =	(User) session.getAttribute("user");
			
			Image image=new Image();
			boolean isBadRequest = false;
			String text=null;
			Album album=null;
			Integer idImage =null;
			List<Comment> comments=new LinkedList<>();
			String nameUser=user.getName() + " " + user.getSurname();
			ImageDAO imageDAO=new ImageDAO(connection);
			AlbumDAO albumDAO =new AlbumDAO(connection);
			CommentDAO commentDAO=new CommentDAO(connection);
			
			
	
			try {
				text=StringEscapeUtils.escapeJava(request.getParameter("text"));
				idImage =Integer.parseInt(request.getParameter("idImage"));
				
			}catch (NumberFormatException | NullPointerException ex) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Incorect parameters values ");
				return;
			}
		
			try {
				
				if(text==null || idImage==null || text.isEmpty()) {
					isBadRequest=true;
				}
			
			} catch (NumberFormatException | NullPointerException e) {
				isBadRequest = true;
				e.printStackTrace();
			}
			if (isBadRequest) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "You cannot insert a blank comment");
				return;
			}
			
		

			try {
					image=imageDAO.getImageById(idImage);
					comments=commentDAO.getCommentFromIdImage(idImage);
					album=albumDAO.getAlbumFromId(image.getIdAlbum());		
			} catch (SQLException e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Incorrect param values for  comment");
				return;
			}
			
			// If the text is more than 180 send an error message
			if (text.length()>180) {
				path = "/WEB-INF/ShowImage.html";
				ServletContext servletContext = getServletContext();
				final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
				ctx.setVariable("errorMsg", "The max number of character must be 180");
				ctx.setVariable("comments", comments);
				ctx.setVariable("image", image);
				ctx.setVariable("album",album );
				templateEngine.process(path, ctx, response.getWriter());
				return;
				}
				
				
				try {
					commentDAO.createComment(idImage, nameUser, text);
				}catch (SQLException e) {
					response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Incorrect param values for  comment");
					return;
				}
				ServletContext servletContext = getServletContext();
				String ctxpath = getServletContext().getContextPath();
				path = ctxpath + "/Image?imageId=" + idImage;
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
	
	

