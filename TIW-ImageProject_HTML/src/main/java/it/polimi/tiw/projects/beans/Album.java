package it.polimi.tiw.projects.beans;

import java.sql.Date;
import java.sql.Timestamp;

public class Album {
	private Integer id;
	private Integer idUser;
	private String title;
	private Timestamp creationDate;
	private String nameCreator;

	
	
	public Album(Integer id, Integer idUser,String title, Timestamp dataCreation, String nameCreator) {
		this.id=id;
		this.idUser=idUser;
		this.title=title;
		this.creationDate=dataCreation;
		this.nameCreator=nameCreator;
		
	}
	
	
	public Integer getId() {
		return this.id;
	}
	
	public Integer getIdUser() {
		return this.idUser;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public Timestamp getCreationDate() {
		return this.creationDate;
	}
	

	
	public String nameCreator() {
		return this.nameCreator;
	}
	
	
	
}
