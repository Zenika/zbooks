package com.zenika.zbooks.entity;

public class Author {

    private String id;
	private String firstName;
	private String lastName;
	
	public Author () {
	}
	
	public Author (String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

    public String getId() {
        return id;
    }

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
}
