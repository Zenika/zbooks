package com.zenika.zbooks.entity;

import com.google.common.base.Objects;

import java.util.ArrayList;
import java.util.List;

public class ZUserHistory {

    private int id;
    private List<ZBook> borrowedBooks;

    public ZUserHistory() {
        borrowedBooks = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ZBook> getBorrowedBooks() {
        return borrowedBooks;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("borrowedBooks", borrowedBooks)
                .toString();
    }
}
