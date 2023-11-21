package com.example.drivermediola;

public class Chat {

    private String sender;
    private String receiver;
    private String message;
    private String imagelink;
    private boolean isseen;

    public Chat(String sender, String receiver, String message, String imagelink, boolean isseen) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.imagelink = imagelink;
        this.isseen = isseen;
    }

    public Chat() {
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImagelink() {
        return imagelink;
    }

    public void setImagelink(String imagelink) {
        this.imagelink = imagelink;
    }

    public boolean isIsseen() {
        return isseen;
    }

    public void setIsseen(boolean isseen) {
        this.isseen = isseen;
    }
}
