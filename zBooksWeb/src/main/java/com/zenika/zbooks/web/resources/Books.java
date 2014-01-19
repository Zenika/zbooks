package com.zenika.zbooks.web.resources;

import com.zenika.zbooks.entity.ZBook;
import com.zenika.zbooks.web.resources.util.Links;

import java.util.ArrayList;
import java.util.List;

public class Books {

    private List<ZBook> books;

    private int numberOfPages;
    private Links links;

    public Books() {
        this.books = new ArrayList<>();
    }

    public List<ZBook> getBooks() {
        return books;
    }

    public void setBooks(List<ZBook> books) {
        this.books = books;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }
}
