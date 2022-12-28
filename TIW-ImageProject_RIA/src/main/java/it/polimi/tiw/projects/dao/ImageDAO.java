package it.polimi.tiw.projects.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polimi.tiw.projects.beans.Album;
import it.polimi.tiw.projects.beans.Image;

public class ImageDAO {
	 private Connection connection;

	 public ImageDAO(Connection connection) {
		this.connection = connection;
	 }
	
	 //Get all the images from the album
	 public  List<Image> getAllImageFromIdAlbum(Integer idAlbum) throws SQLException{
			LinkedList<Image> images= new LinkedList();
			String query = "SELECT * FROM image WHERE idAlbum = ?";
			try (PreparedStatement pstatement = connection.prepareStatement(query);) {
				pstatement.setInt(1, idAlbum);
				try (ResultSet result = pstatement.executeQuery();) {	
					while (result.next()) {
						Image image= new Image();
						image.setFilePath(result.getString("filePath"));
						image.setId(result.getInt("id"));
						image.setIdAlbum(result.getInt("idAlbum"));
						image.setTitle(result.getString("title"));
						image.setTextDescription(result.getString("textDescription"));
						images.add(image);
						
					}
				}
			}
			catch (SQLException e) {
				System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
				throw e;
			}
			return images;
		}

	 	//Return all the images from the user which do not have an album associated
		public  List<Image> getAllImageFromIdUser(Integer idUser) throws SQLException{
			List<Image> images= new LinkedList();
			String query = "SELECT * FROM image WHERE idAlbum IS NULL AND idUser= ?";
			try (PreparedStatement pstatement = connection.prepareStatement(query);) {
				pstatement.setInt(1, idUser);
				try (ResultSet result = pstatement.executeQuery();) {
					while (result.next()) {
						Image image= new Image();
						image.setFilePath(result.getString("filePath"));
						image.setId(result.getInt("id"));
						image.setIdAlbum(result.getInt("idAlbum"));
						image.setTitle(result.getString("title"));
						image.setTextDescription(result.getString("textDescription"));
						images.add(image);
						
					}
				}
			}
			catch (SQLException e) {
				System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
				throw e;
			}
			return images;
		}
		
		//Add an image to the specified album
		public void addImage(Integer idImage, Integer idAlbum) throws SQLException {
			String query = "UPDATE image SET idAlbum= ? WHERE id= ? AND idAlbum IS NULL ";
			try (PreparedStatement pstatement = connection.prepareStatement(query);) {
				pstatement.setInt(1, idAlbum);
				pstatement.setInt(2, idImage);
				pstatement.executeUpdate();
			}
			catch (SQLException e) {
				System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
				throw e;
			}
		}
		
		//Return the id album of the image
		public Integer getIdAlbum(Integer idImage) throws SQLException {
			Integer imageId=null;;
			String query = "SELECT idAlbum FROM image WHERE id=?";
			try (PreparedStatement pstatement = connection.prepareStatement(query);) {
				pstatement.setInt(1, idImage);
				try (ResultSet result = pstatement.executeQuery();) {
					while (result.next()) {
						imageId=result.getInt("idAlbum");
					}
				}
			}catch (SQLException e) {
				System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
				throw e;
			}
			return imageId;
		}
		
		//Return the image from the id
		public Image getImageById(Integer idImage) throws SQLException {
			Image image= new Image();
			String query = "SELECT * FROM image WHERE id=?";
			try (PreparedStatement pstatement = connection.prepareStatement(query);) {
				pstatement.setInt(1, idImage);
				try (ResultSet result = pstatement.executeQuery();) {
					while (result.next()) {	
						image.setFilePath(result.getString("filePath"));
						image.setId(result.getInt("id"));
						image.setIdAlbum(result.getInt("idAlbum"));
						image.setTitle(result.getString("title"));
						image.setTextDescription(result.getString("textDescription"));	
					}
				}
			}
			catch (SQLException e) {
				System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
				throw e;
			}
			
			return image;
		}

	
}
	
