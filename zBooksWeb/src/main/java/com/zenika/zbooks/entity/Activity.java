package com.zenika.zbooks.entity;

import java.util.Date;

public class Activity {

    private int id;
    private ActivityType type;
    private Date date;
    private ZUser user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ActivityType getType() {
        return type;
    }

    public void setType(ActivityType type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ZUser getUser() {
        return user;
    }

    public void setUser(ZUser user) {
        this.user = user;
    }
}
