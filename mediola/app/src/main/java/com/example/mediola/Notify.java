package com.example.mediola;

public class Notify {

    String docid,message,sender,receiver;

    public Notify() {
    }

    public Notify(String docid, String message, String sender, String receiver) {
        this.docid = docid;
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
}
