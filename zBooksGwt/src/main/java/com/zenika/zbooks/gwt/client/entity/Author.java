package com.zenika.zbooks.gwt.client.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="AUTHORS")
public class Author {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String firstName;
	
	private String lastName;
//	
//	@ManyToMany(fetch = FetchType.LAZY, mappedBy="authors")
//	private List<ZBook> books;
	
	public Author () {
		
	}
	
	public Author (String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public int getId () {
		return this.id;
	}
	
	public void setId (int id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

//	public List<ZBook> getBooks() {
//		return books;
//	}
//
//	public void setBooks(List<ZBook> books) {
//		this.books = books;
//	}
}
