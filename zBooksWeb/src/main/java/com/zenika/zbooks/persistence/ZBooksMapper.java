package com.zenika.zbooks.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zenika.zbooks.entity.Author;
import com.zenika.zbooks.entity.ZBook;

public interface ZBooksMapper {
	
	void addBook (ZBook book);
	
	void deleteBook (int isbn);

	ZBook getBook (int isbn);
	
	List<Author> selectAllAuthorsByBook(int isbn);
	
	List<Author> selectAllAuthors ();
	
	List<ZBook> getAllBooks ();
	
	Author getAuthorById (int id);
	
	Author getAuthorByName (@Param("firstName") String firstName, @Param("lastName") String lastName);
	
	void addAuthor(Author author);
	
}
