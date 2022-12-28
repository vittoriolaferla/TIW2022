package it.polimi.tiw.projects.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.polimi.tiw.projects.beans.User;

public class UserDAO {
	private Connection con;

	public UserDAO(Connection connection) {
		this.con = connection;
	}

	public User checkCredentials(String usrn, String pwd) throws SQLException {
		String query = "SELECT  id, username, name, surname FROM user  WHERE username = ? AND password =?";
		try (PreparedStatement pstatement = con.prepareStatement(query);) {
			pstatement.setString(1, usrn);
			pstatement.setString(2, pwd);
			try (ResultSet result = pstatement.executeQuery();) {
				if (!result.isBeforeFirst()) // no results, credential check failed
					return null;
				else {
					result.next();
					User user = new User();
					user.setId(result.getInt("id"));
					user.setUsername(result.getString("username"));
					user.setName(result.getString("name"));
					user.setSurname(result.getString("surname"));
					return user;
				}
			}
		}
	}
	
	//Chech if the user already exists
	public Boolean checkAlreadyExists(String usrn) throws SQLException {
	
		String query = "SELECT username FROM user  WHERE username = ?";
		try (PreparedStatement pstatement = con.prepareStatement(query);) {
			pstatement.setString(1, usrn);
			try (ResultSet result = pstatement.executeQuery();) {
				if (!result.isBeforeFirst()) // no results, credential check failed
					return false;
				else {
					return true;
				}
		
			}
		}
	}
	
	

	public void addNewUser(String user, String passw,String name, String surname)
			throws SQLException {
		String email=null;
		String query = "INSERT into user (username, password, name, surname, email) VALUES(?, ?, ?, ?, ?)";
		// Delimit the transaction explicitly, not to leave the db in inconsistent state
		con.setAutoCommit(false);
		try (PreparedStatement pstatement = con.prepareStatement(query);) {
			pstatement.setString(1, user);
			pstatement.setString(2, passw);
			pstatement.setString(3, name);
			pstatement.setString(4, surname);
			pstatement.setString(5, email);
			Integer row=pstatement.executeUpdate(); // 1st update
			System.out.print(row);
			// 2nd update
			con.commit();
			
		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
			con.rollback(); // if update 1 OR 2 fails, roll back all work
			throw e;
		}finally {
			con.setAutoCommit(true);
		}
	}
}

	
