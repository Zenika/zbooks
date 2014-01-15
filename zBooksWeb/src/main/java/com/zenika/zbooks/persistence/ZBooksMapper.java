package com.zenika.zbooks.persistence;

import com.zenika.zbooks.entity.ZBook;
import com.zenika.zbooks.entity.ZUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ZBooksMapper {

    List<ZBook> getBooks();

    ZBook getBook(int id);

    void addBook(ZBook book);

    void deleteBook(int id);

    void updateBook(ZBook book);

    /**
     * Method to update the DB to borrow a zBook.
     * @param book
     * @param user
     */
    void borrowBook (
            @Param("book") ZBook book,
            @Param("user") ZUser user);

    /**
     * Method to update the DB to borrow or return a zBook
     * @param book
     */
    void returnBook (ZBook book);
}
