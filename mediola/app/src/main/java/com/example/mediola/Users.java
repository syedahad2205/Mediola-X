package com.example.mediola;

public class Users {

    private  String id,fname,lname,email,aadhaarno,Phoneno,imageURL,search,status,address,type;

    public Users() {
    }

    public Users(String id, String fname, String lname, String email, String aadhaarno, String phoneno, String imageURL, String search, String status, String address, String type) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.aadhaarno = aadhaarno;
        Phoneno = phoneno;
        this.imageURL = imageURL;
        this.search = search;
        this.status = status;
        this.address = address;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAadhaarno() {
        return aadhaarno;
    }

    public void setAadhaarno(String aadhaarno) {
        this.aadhaarno = aadhaarno;
    }

    public String getPhoneno() {
        return Phoneno;
    }

    public void setPhoneno(String phoneno) {
        Phoneno = phoneno;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}