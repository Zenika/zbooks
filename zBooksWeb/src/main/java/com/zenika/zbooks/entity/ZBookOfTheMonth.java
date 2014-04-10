package com.zenika.zbooks.entity;

public class ZBookOfTheMonth {

    private ZBook book;

    private ZUser submitter;

    private int supporters;

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

    public int getSupporters(){
        return supporters;
    }       
}
