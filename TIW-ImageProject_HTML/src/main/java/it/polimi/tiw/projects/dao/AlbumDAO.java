package it.polimi.tiw.projects.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import it.polimi.tiw.projects.beans.Album;


public class AlbumDAO {
	private Connection connection;

	public AlbumDAO(Connection connection) {
		this.connection = connection;
	}
	
	
	public  List<Album> getAllAlbumsByUser(Integer idUser) throws SQLException{
		
		List<Album> albums= new LinkedList();
		
		String query = "SELECT * FROM album WHERE idUser= ? order by creationDate desc";
		try (PreparedStatement pstatement = connection.prepareStatement(query);) {
			pstatement.setInt(1, idUser);
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					Album album= new Album(result.getInt("id"),result.getInt("idUser"), result.getString("title"), result.getTimestamp("creationDate"), result.getString("nameCreator"));
					albums.add(album);
				}
			}
		}
		catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
			throw e;
		}
		return albums;
	}
	
public  List<Album> getAllAlbumsNotOwnedByUser(Integer idUserCreator) throws SQLException{
		
		List<Album> albums= new LinkedList();
		
		String query = "SELECT * FROM album ";
		// example of try with resources, just for fun
		// https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
		try (PreparedStatement pstatement = connection.prepareStatement(query);) {
			
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					if(!(idUserCreator.equals((result).getInt("idUser")))) {
				Album album= new Album(result.getInt("id"),result.getInt("idUser"), result.getString("title"), result.getTimestamp("creationDate"), result.getString("nameCreator"));
			
					albums.add(album);
				
					}
				}
			}
		}
		catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
			throw e;
		}
		return albums;
	}


	public void createAlbum(int idUser,String title, String nameCreator, Timestamp creationDate) throws SQLException {
		String query = "INSERT into album (idUser, title, nameCreator,creationDate) VALUES(?, ?, ?, ?)";
		try (PreparedStatement pstatement = connection.prepareStatement(query);) {
			pstatement.setInt(1,idUser );
			pstatement.setString(2, title);
			pstatement.setString(3, nameCreator);
			pstatement.setTimestamp(4, creationDate);
			pstatement.executeUpdate();  // what does it returns??
		}catch (SQLException e) {
			System.out.print("create albumDAO");
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
			throw e;
			}
		 
	}
	
	public Integer getLastAlbumFromIdUser(Integer idUser)throws SQLException  {
		Integer idUserSelected=null;
		String query = "SELECT max(id) from album where idUser=?";
		try (PreparedStatement pstatement = connection.prepareStatement(query);) {
			pstatement.setInt(1, idUser);
				try (ResultSet result = pstatement.executeQuery();) {
					while (result.next()) {
					idUserSelected=result.getInt("max(id)");
					}
				}
		}
		catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
			throw e;
		}
		return idUserSelected;
		}
	
	
	
	public Album getAlbumFromId(Integer idAlbum) throws SQLException {
		Album album=null;
		String query = "SELECT * FROM album WHERE id=?";
	try (PreparedStatement pstatement = connection.prepareStatement(query);) {
		pstatement.setInt(1, idAlbum);
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
				 album= new Album(result.getInt("id"),result.getInt("idUser"), result.getString("title"), result.getTimestamp("creationDate"), result.getString("nameCreator"));
				}
			}
	}
	catch (SQLException e) {
		System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		throw e;
	}
	return album;
	}

	

	
}

