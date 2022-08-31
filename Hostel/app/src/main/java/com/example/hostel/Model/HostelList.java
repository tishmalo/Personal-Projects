package com.example.hostel.Model;

public class HostelList {

    private String room, space;

    public HostelList() {
    }

    public HostelList(String room, String space) {
        this.room = room;
        this.space = space;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getSpace() {
        return space;
    }

    public void setSpace(String space) {
        this.space = space;
    }
}
