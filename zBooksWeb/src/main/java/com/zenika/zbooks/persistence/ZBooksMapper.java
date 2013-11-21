package com.zenika.zbooks.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zenika.zbooks.entity.Author;
import com.zenika.zbooks.entity.ZBook;

public interface ZBooksMapper {
	
	void addBook (ZBook book);
	
	void deleteBook (int id);

	ZBook getBook (int id);

	List<ZBook> getBooks ();

	void addAuthor(Author author);

	void addLinkAuthorZBooks(@Param("ISBN") String isbn, @Param("authorId") int id);

    Author getAuthorByName (@Param("firstName") String firstName, @Param("lastName") String lastName);
}
