package com.zenika.zbooks.gwt.client.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="ZBOOKS")
public class ZBook implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3087040524386504839L;
	/**
	 * 
	 */
	@Id
	private int ISBN;
	
	private String edition;
	
	private String title;
	
	private int pagesNumber;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name="LINK_ZBOOKS_AUTHORS",
			joinColumns={@JoinColumn(name="ZBOOK_ISBN", referencedColumnName="ISBN")},
				      inverseJoinColumns={@JoinColumn(name="AUTHOR_ID", referencedColumnName="ID")})
	private List<Author> authors;
	
//	private Date releaseDate;
//	private Language language;
//	private ZenikaCollection collection;
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

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}
//
//	public Date getReleaseDate() {
//		return releaseDate;
//	}
//
//	public void setReleaseDate(Date releaseDate) {
//		this.releaseDate = releaseDate;
//	}

//	public String getLanguage() {
//		return language.toString().toUpperCase();
//	}
//
//	public void setLanguage(String language) {
//		this.language = Language.getValueOfToUpperCase(language);
//	}
//
//	public String getCollection() {
//		return collection.toString().toUpperCase();
//	}
//
//	public void setCollection(String collection) {
//		this.collection = ZenikaCollection.getValueOfToUpperCase(collection);
//	}

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
	
}
