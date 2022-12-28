package it.polimi.tiw.projects.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import it.polimi.tiw.projects.beans.*;



public class CommentDAO {

	private Connection connection;

	public CommentDAO(Connection connection) {
		this.connection = connection;
	}
	
	public List<Comment> getCommentFromIdImage(Integer idImage) throws SQLException{
		List<Comment> comments= new LinkedList();
		String query = "SELECT * FROM comment WHERE idImage= ? ";
		try (PreparedStatement pstatement = connection.prepareStatement(query);) {
			pstatement.setInt(1, idImage);
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					Comment comment=new Comment();
					comment.setId(result.getInt("id"));
					comment.setIdImage(result.getInt("idImage"));
					comment.setText(result.getString("text"));
					comment.setNameUser(result.getString("nameUser"));
					comments.add(comment);
				}
			}
		}
		catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
			throw e;
		}
		return comments;
	}
	
	
	public void createComment(Integer idImage, String nameUser, String text) throws SQLException {
		Comment comment=new Comment();
		String query="INSERT into comment (idImage, nameUser, text) VALUES(?, ?, ?)";
		try (PreparedStatement pstatement = connection.prepareStatement(query);) {
			pstatement.setInt(1,idImage);
			pstatement.setString(2, nameUser);
			pstatement.setString(3, text);
			pstatement.executeUpdate();  
		}catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
			throw e;
			}
		}

}
	

