package com.example.studentsportal.Model;

public class ResultsModel {

    private String code, result;

    public ResultsModel() {
    }

    public ResultsModel(String code, String result) {
        this.code = code;
        this.result = result;
    }

    public String getcode() {
        return code;
    }

    public void setcode(String code) {
        this.code = code;
    }

    public String getresult() {
        return result;
    }

    public void setresult(String result) {
        this.result = result;
    }
}
