package com.zenika.zbooks.web.resources;

import com.zenika.zbooks.entity.ZBook;
import com.zenika.zbooks.web.resources.util.Link;

import java.util.ArrayList;
import java.util.List;

public class Books {

    private List<ZBook> books;
    private int numberOfPages;
    private List<Link> links;

    public Books() {
        this.books = new ArrayList<>();
        this.links = new ArrayList<>();
    }

    public List<ZBook> getBooks() {
        return books;
    }

    public void setBooks(List<ZBook> books) {
        this.books = books;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public void addLink(Link link) {
        this.links.add(link);
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }
}
