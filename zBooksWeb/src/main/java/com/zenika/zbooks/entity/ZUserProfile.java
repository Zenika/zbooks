package com.zenika.zbooks.entity;

import com.google.common.base.Objects;

import java.util.List;

public class ZUserProfile {

    private int id;
    private List<String> interests;
    private ZUserHistory history;

    public ZUserProfile() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("history", history)
                .toString();
    }


}
