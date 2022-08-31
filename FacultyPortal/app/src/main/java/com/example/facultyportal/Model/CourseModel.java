package com.example.facultyportal.Model;

public class CourseModel {

    private String code, title;

    public CourseModel() {
    }

    public CourseModel(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getcode() {
        return code;
    }

    public void setcode(String code) {
        this.code = code;
    }

    public String gettitle() {
        return title;
    }

    public void settitle(String title) {
        this.title = title;
    }
}
