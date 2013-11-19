package com.zenika.zbooks.gwt.entity;

public class Borrower {

	private final String firstName;
	private final String lastName;
	private String email;
	
	public Borrower (String firstName, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
