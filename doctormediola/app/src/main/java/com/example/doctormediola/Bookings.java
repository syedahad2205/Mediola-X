package com.example.doctormediola;

public class Bookings {

    String date,docid,time,userid,reason,confirm,imglink,name;

    public Bookings() {
    }

    public Bookings(String date, String docid, String time, String userid, String reason, String confirm, String imglink, String name) {
        this.date = date;
        this.docid = docid;
        this.time = time;
        this.userid = userid;
        this.reason = reason;
        this.confirm = confirm;
        this.imglink = imglink;
        this.name = name;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getImglink() {
        return imglink;
    }

    public void setImglink(String imglink) {
        this.imglink = imglink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
