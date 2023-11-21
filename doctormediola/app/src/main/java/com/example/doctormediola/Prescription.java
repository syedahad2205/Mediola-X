package com.example.doctormediola;

public class Prescription {

   String fname,id,imageURL,lname,location,docid,userid,username,prestxt,pno,date,time;

    public Prescription() {
    }

    public Prescription(String fname, String id, String imageURL, String lname, String location, String docid, String userid, String username, String prestxt, String pno, String date, String time) {
        this.fname = fname;
        this.id = id;
        this.imageURL = imageURL;
        this.lname = lname;
        this.location = location;
        this.docid = docid;
        this.userid = userid;
        this.username = username;
        this.prestxt = prestxt;
        this.pno = pno;
        this.date = date;
        this.time = time;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getPrestxt() {
        return prestxt;
    }

    public void setPrestxt(String prestxt) {
        this.prestxt = prestxt;
    }

    public String getPno() {
        return pno;
    }

    public void setPno(String pno) {
        this.pno = pno;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
