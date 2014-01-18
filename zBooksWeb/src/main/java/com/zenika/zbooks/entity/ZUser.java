package com.zenika.zbooks.entity;

public class ZUser {

    private int id;
    private String email;
    private String userName;
    private String password;
    private ZPower zPower;
//    private List<ZBook> borrowedBooks;
    private ZUserProfile profile;

    public ZUser() {
        zPower = ZPower.USER;
//        borrowedBooks = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ZPower getZPower() {
        return zPower;
    }

    public void setZPower(ZPower zPower) {
        this.zPower = zPower;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

//	public List<ZBook> getBorrowedBooks() {
//		return borrowedBooks;
//	}
//
//	public void setBorrowedBooks(List<ZBook> borrowedBooks) {
//		this.borrowedBooks = borrowedBooks;
//	}
	
//	public void borrowBook (ZBook zBook) {
//		this.borrowedBooks.add(zBook);
//	}
	
//	public void returnBook(String isbn) {
//		for (ZBook zBook : borrowedBooks) {
//			if (zBook.getISBN().equals(isbn)) {
//				borrowedBooks.remove(zBook);
//			}
//		}
//	}

    public ZUserProfile getProfile() {
        return profile;
    }

    public void setProfile(ZUserProfile profile) {
        this.profile = profile;
    }
}
