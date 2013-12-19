package com.zenika.zbooks.persistence;

import com.zenika.zbooks.entity.ZBook;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ZBooksMapper {

    List<ZBook> getBooks();

    ZBook getBook(int id);

    void addBook(ZBook book);

    void deleteBook(int id);

    void updateBook(ZBook book);
}
