package com.zenika.zbooks.web.resources;

import com.zenika.zbooks.web.resources.util.Links;

import java.awt.print.Book;
import java.util.List;

public class Books {

    private List<Book> books;

    private Links links;

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }
}
