package com.example.studentsportal.Model;

public class CourseCode {

    private String Code, Email, UserId;

    public CourseCode() {
    }


    public CourseCode(String code, String email, String userId) {
        Code = code;
        Email = email;
        UserId = userId;
    }

    public String getuserId() {
        return UserId;
    }

    public void setuserId(String userId) {
        UserId = userId;
    }

    public String getcode() {
        return Code;
    }

    public void setcode(String code) {
        Code = code;
    }

    public String getemail() {
        return Email;
    }

    public void setemail(String email) {
        Email = email;
    }
}
