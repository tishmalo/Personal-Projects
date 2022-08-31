package com.example.studentsportal.Model;

public class ChatModel {

    private String sender,receiver,message,Email;

    public ChatModel() {
    }

    public ChatModel(String sender, String receiver, String message, String email) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        Email = email;
    }

    public String getemail() {
        return Email;
    }

    public void setemail(String email) {
        Email = email;
    }

    public String getsender() {
        return sender;
    }

    public void setsender(String sender) {
        this.sender = sender;
    }

    public String getreceiver() {
        return receiver;
    }

    public void setreceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getmessage() {
        return message;
    }

    public void setmessage(String message) {
        this.message = message;
    }
}
