package com.example.studentsportal.Model;

public class DownloadModel {

    private String code, document, name;

    public DownloadModel() {
    }

    public DownloadModel(String code, String document, String name) {
        this.code = code;
        this.document = document;
        this.name = name;
    }

    public String getcode() {
        return code;
    }

    public void setcode(String code) {
        this.code = code;
    }

    public String getdocument() {
        return document;
    }

    public void setdocument(String document) {
        this.document = document;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }
}
