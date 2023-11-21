package com.example.drivermediola;

public class Likes {

    private String liked,postid;

    public Likes() {

    }

    public Likes(String liked, String postid) {
        this.liked = liked;
        this.postid = postid;
    }

    public String getLiked() {
        return liked;
    }

    public void setLiked(String liked) {
        this.liked = liked;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }
}