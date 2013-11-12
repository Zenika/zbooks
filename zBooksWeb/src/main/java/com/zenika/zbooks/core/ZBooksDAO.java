package com.zenika.zbooks.core;

import com.zenika.zbooks.entity.ZBook;

public interface ZBooksDAO {
	
	void addBook (String isbn);
	
	void addBook (ZBook book);
	
	void deleteBook (String isbn);
	
	void deleteBook (ZBook book);
	
	ZBook getBook (String isbn);
	
	void modifyBook (ZBook newBook);
	
}
