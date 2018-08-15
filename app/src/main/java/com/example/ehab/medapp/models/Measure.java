package com.example.ehab.medapp.models;

public class Measure {
    String date,measure,comment;

    public Measure( String measure, String comment,String date) {
        this.date = date;

        this.measure = measure;
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public String getMeasure() {
        return measure;
    }

    public String getComment() {
        return comment;
    }
}
