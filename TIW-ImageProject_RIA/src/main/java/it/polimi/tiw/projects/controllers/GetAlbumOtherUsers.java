package it.polimi.tiw.projects.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.polimi.tiw.projects.beans.Album;
import it.polimi.tiw.projects.beans.User;
import it.polimi.tiw.projects.dao.AlbumDAO;
import it.polimi.tiw.projects.utils.ConnectionHandler;
@WebServlet("/GetAlbumOtherUsers")
@MultipartConfig
public class GetAlbumOtherUsers extends HttpServlet {

	

		private static final long serialVersionUID = 1L;
		private Connection connection = null;

		public GetAlbumOtherUsers() {
			super();
		}
		
		public void init() throws ServletException {
			connection = ConnectionHandler.getConnection(getServletContext());
		}
	/**
	 * Get all the images from the user, which are not associated with an album
	 */
		protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			// If the user is not logged in (not present in session) redirect to the login
			HttpSession session = request.getSession();
		
			User user = (User) session.getAttribute("user");
			AlbumDAO albumDAO = new AlbumDAO(connection);
			List<Album> albumsNotOwnedByUser = new ArrayList<Album>();

			
			try {
				albumsNotOwnedByUser=albumDAO.getAllAlbumsNotOwnedByUser(user.getId());
			} catch (SQLException e) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.getWriter().println("Incorrect param values for  album");
				return;
			}
			Gson gson = new GsonBuilder()
					   .setDateFormat("yyyy MMM dd").create();
			String json = gson.toJson(albumsNotOwnedByUser);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
			
		}
		
		protected void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			doGet(request, response);
		}

		public void destroy() {
			try {
				ConnectionHandler.closeConnection(connection);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}


