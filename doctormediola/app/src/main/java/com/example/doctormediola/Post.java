package com.example.doctormediola;

public class Post {
    private String date, imagelink, name, postid, time, userimg, totallike, title, dis,approved;

    public Post() {
    }

    public Post(String date, String imagelink, String name, String postid, String time, String userimg, String totallike, String title, String dis, String approved) {
        this.date = date;
        this.imagelink = imagelink;
        this.name = name;
        this.postid = postid;
        this.time = time;
        this.userimg = userimg;
        this.totallike = totallike;
        this.title = title;
        this.dis = dis;
        this.approved = approved;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImagelink() {
        return imagelink;
    }

    public void setImagelink(String imagelink) {
        this.imagelink = imagelink;
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

    public String getTotallike() {
        return totallike;
    }

    public void setTotallike(String totallike) {
        this.totallike = totallike;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDis() {
        return dis;
    }

    public void setDis(String dis) {
        this.dis = dis;
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }
}

