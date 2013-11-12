package com.zenika.zbooks.entity;

import java.util.Date;

public class ZBook {

	private String ISBN;
	private String edition;
	private String title;
	private int pagesNumber;
	private Author[] author;
	private Date releaseDate;
	private Language language;
	private ZenikaCollection collection;
	private boolean ebook = false;
	private boolean paper = false;
	private Borrower borrower;
	private String summary;
	
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

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public ZenikaCollection getCollection() {
		return collection;
	}

	public void setCollection(ZenikaCollection collection) {
		this.collection = collection;
	}

	public boolean isEbook() {
		return ebook;
	}

	public void setEbook(boolean ebook) {
		this.ebook = ebook;
	}

	public boolean isPaper() {
		return paper;
	}

	public void setPaper(boolean paper) {
		this.paper = paper;
	}

	public Borrower getBorrower() {
		return borrower;
	}

	public void setBorrower(Borrower borrower) {
		this.borrower = borrower;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
}
