package com.zenika.zbooks.entity;

public class ZBook {

    private int id;
    private String ISBN;
    private String edition;
    private String title;
    private int pagesNumber;
    private String authors;
    private String releaseDate;
    private String language;
    private String cover;
    private ZCollection zCollection;
    private String borrowerName="";

    public ZBook() {
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

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

	public ZCollection getZCollection() {
		return zCollection;
	}

	public void setZCollection(ZCollection collection) {
		this.zCollection = collection;
	}

	public String getBorrowerName() {
		return this.borrowerName;
	}

	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}

    public boolean isValid() {
        if (ISBN == null || ISBN.isEmpty()) {
            return false;
        }
        if (title == null || title.isEmpty()) {
            return false;
        }
        return true;
    }
}
