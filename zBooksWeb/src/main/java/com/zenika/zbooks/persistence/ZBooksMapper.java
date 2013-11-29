package com.zenika.zbooks.persistence;

import com.zenika.zbooks.entity.ZBook;
import java.util.List;

public interface ZBooksMapper {

    List<ZBook> getBooks();

    ZBook getBook(int id);

    void addBook(ZBook book);

    void deleteBook(int id);

    void updateBook(ZBook book);

    void resetBdd();

}
