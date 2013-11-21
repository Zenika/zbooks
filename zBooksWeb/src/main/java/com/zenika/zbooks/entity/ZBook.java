package com.zenika.zbooks.entity;

import java.util.ArrayList;
import java.util.Date;

public class ZBook {

    private int id;
	private String ISBN;
	private String edition;
	private String title;
	private int pagesNumber;
	private ArrayList<Author> authors;
	private Date releaseDate;
	private Language language;
	
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

	public ArrayList<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(ArrayList<Author> authors) {
		this.authors = authors;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getLanguage() {
		return language.toString();
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }
}
