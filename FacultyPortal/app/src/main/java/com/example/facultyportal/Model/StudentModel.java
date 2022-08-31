package com.example.facultyportal.Model;

public class StudentModel {

    private String Course, Email, Reg, UserId;

    public StudentModel() {
    }


    public StudentModel(String course, String email, String reg, String userId) {
        Course = course;
        Email = email;
        Reg = reg;
        UserId = userId;
    }


    public String getCourse() {
        return Course;
    }

    public void setCourse(String course) {
        Course = course;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getReg() {
        return Reg;
    }

    public void setReg(String reg) {
        Reg = reg;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
