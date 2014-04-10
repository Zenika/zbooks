package com.zenika.zbooks.entity;

import  java.util.List;

public class ZBookOfTheMonth {

    private ZBook book;

    private ZUser submitter;

    private List<ZUser> supporters;

    public ZBook getBook(){
        return book;
    }

    public void setBook(ZBook book){
        this.book = book;
    }

    public ZUser getSubmitter(){
        return submitter;
    }    


    public void setSubmitter(ZUser submitter){
        this.submitter = submitter;
    }

    public List<ZUser> getSupporter(){
        return supporters;
    }    


    public void setSupporter(List<ZUser> supporters){
        this.supporters = supporters;
    }

}
