package com.zenika.zbooks.entity;

import java.util.ArrayList;
import java.util.Date;

public class ZBook {

	private int ISBN;
	private String edition;
	private String title;
	private int pagesNumber;
	private ArrayList<Author> authors;
	private Date releaseDate;
	private Language language;
	private ZenikaCollection collection;
//	private boolean ebook = false;
//	private boolean paper = false;
//	private Borrower borrower;
//	private String summary;
	
	public ZBook () {
		
	}

	public int getISBN() {
		return ISBN;
	}

	public void setISBN(int iSBN) {
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
		return language.getValue();
	}

	public void setLanguage(String language) {
		this.language = Language.valueOf(language);
	}

	public String getCollection() {
		return collection.toString();
	}

	public void setCollection(String collection) {
		this.collection = ZenikaCollection.valueOf(collection);
	}

//	public boolean isEbook() {
//		return ebook;
//	}
//
//	public void setEbook(boolean ebook) {
//		this.ebook = ebook;
//	}
//
//	public boolean isPaper() {
//		return paper;
//	}
//
//	public void setPaper(boolean paper) {
//		this.paper = paper;
//	}
//
//	public Borrower getBorrower() {
//		return borrower;
//	}
//
//	public void setBorrower(Borrower borrower) {
//		this.borrower = borrower;
//	}
//
//	public String getSummary() {
//		return summary;
//	}
//
//	public void setSummary(String summary) {
//		this.summary = summary;
//	}
	
	@Override
	public String toString () {
		return this.title + " de " + this.authors + " -- ISBN : " + this.ISBN;
	}
}
