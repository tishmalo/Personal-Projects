package com.example.facultyportal.Model;

public class UnitsModel {

    private String code, email, userId;

    public UnitsModel() {
    }


    public UnitsModel(String code, String email, String userId) {
        this.code = code;
        this.email = email;
        this.userId = userId;
    }

    public String getcode() {
        return code;
    }

    public void setcode(String code) {
        this.code = code;
    }

    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }

    public String getuserId() {
        return userId;
    }

    public void setuserId(String userId) {
        this.userId = userId;
    }
}
