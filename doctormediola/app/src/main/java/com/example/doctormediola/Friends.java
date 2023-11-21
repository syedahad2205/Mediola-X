package com.example.doctormediola;

public class Friends {

    private String sender,receiver,friend;

    public Friends() {
    }

    public Friends(String sender, String receiver, String friend) {

        this.sender = sender;
        this.receiver = receiver;
        this.friend = friend;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getFriend() {
        return friend;
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }
}
