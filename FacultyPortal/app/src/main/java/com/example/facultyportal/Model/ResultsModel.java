package com.example.facultyportal.Model;

public class ResultsModel {

    private String Reg,cat,exams,assignment,result, code;

    public ResultsModel() {
    }

    public ResultsModel(String reg, String cat, String exams, String assignment, String result, String code) {
        Reg = reg;
        this.cat = cat;
        this.exams = exams;
        this.assignment = assignment;
        this.result = result;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getreg() {
        return Reg;
    }

    public void setreg(String reg) {
        Reg = reg;
    }

    public String getcat() {
        return cat;
    }

    public void setcat(String cat) {
        this.cat = cat;
    }

    public String getexams() {
        return exams;
    }

    public void setexams(String exams) {
        this.exams = exams;
    }

    public String getassignment() {
        return assignment;
    }

    public void setassignment(String assignment) {
        this.assignment = assignment;
    }

    public String getresult() {
        return result;
    }

    public void setresult(String result) {
        this.result = result;
    }
}
