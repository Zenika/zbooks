package com.zenika.zbooks.entity;

public class ZBook {

	private String ISBN;
	private String edition;
	private String title;
	private int pagesNumber;
	private Author[] author;
	
	public ZBook () {
		
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getPagesNumber() {
		return pagesNumber;
	}

	public void setPagesNumber(int pagesNumber) {
		this.pagesNumber = pagesNumber;
	}

	public Author[] getAuthor() {
		return author;
	}

	public void setAuthor(Author[] author) {
		this.author = author;
	}
}
