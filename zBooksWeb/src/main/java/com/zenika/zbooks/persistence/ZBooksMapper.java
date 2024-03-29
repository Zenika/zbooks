package com.zenika.zbooks.persistence;

import com.zenika.zbooks.entity.ZBook;
import com.zenika.zbooks.entity.ZUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZBooksMapper {

    List<ZBook> getBooks();

    List<ZBook> getBooksOfPage(@Param("currentPage") int currentPage,
                               @Param("numberOfElementPerPage") int numberOfElementPerPage,
                               @Param("sortBy") String sortBy,
                               @Param("order") String order);

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

    int getNumberOfBooks();
}
