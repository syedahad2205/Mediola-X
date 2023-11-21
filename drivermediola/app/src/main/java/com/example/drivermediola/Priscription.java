package com.example.drivermediola;

public class Priscription {

    String driverid,docid,userid,username,prestext,time,date,pno,confirm,location;


    public Priscription() {
    }

    public Priscription(String driverid, String docid, String userid, String username, String prestext, String time, String date, String pno, String confirm, String location) {
        this.driverid = driverid;
        this.docid = docid;
        this.userid = userid;
        this.username = username;
        this.prestext = prestext;
        this.time = time;
        this.date = date;
        this.pno = pno;
        this.confirm = confirm;
        this.location = location;
    }

    public String getDriverid() {
        return driverid;
    }

    public void setDriverid(String driverid) {
        this.driverid = driverid;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPno() {
        return pno;
    }

    public void setPno(String pno) {
        this.pno = pno;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
