package com.example.mediola;

public class Priscription {
   String date,docid,prestext,time,userid,username;

    public Priscription() {
    }

    public Priscription(String date, String docid, String prestext, String time, String userid, String username) {
        this.date = date;
        this.docid = docid;
        this.prestext = prestext;
        this.time = time;
        this.userid = userid;
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getPrestext() {
        return prestext;
    }

    public void setPrestext(String prestext) {
        this.prestext = prestext;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
