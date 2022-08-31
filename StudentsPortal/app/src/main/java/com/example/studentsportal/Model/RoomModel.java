package com.example.studentsportal.Model;

public class RoomModel {

    private String Room, space, reg;

    public RoomModel() {
    }


    public RoomModel(String room, String space, String reg) {
        Room = room;
        this.space = space;
        this.reg = reg;
    }

    public String getroom() {
        return Room;
    }

    public void setroom(String room) {
        Room = room;
    }

    public String getspace() {
        return space;
    }

    public void setspace(String space) {
        this.space = space;
    }

    public String getreg() {
        return reg;
    }

    public void setreg(String reg) {
        this.reg = reg;
    }
}
