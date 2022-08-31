package com.example.studentsportal.Model;

public class BookingModel {

    private String Reg, Room;

    public BookingModel() {
    }

    public BookingModel(String reg, String room) {
        Reg = reg;
        Room = room;
    }

    public String getreg() {
        return Reg;
    }

    public void setreg(String reg) {
        Reg = reg;
    }

    public String getroom() {
        return Room;
    }

    public void setroom(String room) {
        Room = room;
    }
}
