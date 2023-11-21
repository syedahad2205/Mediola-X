package com.example.doctormediola;

public class Comments {

    private String comment,date,name,postid,time,userimg;

    public Comments() {
    }

    public Comments(String comment, String date, String name, String postid, String time, String userimg) {
        this.comment = comment;
        this.date = date;
        this.name = name;
        this.postid = postid;
        this.time = time;
        this.userimg = userimg;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserimg() {
        return userimg;
    }

    public void setUserimg(String userimg) {
        this.userimg = userimg;
    }
}