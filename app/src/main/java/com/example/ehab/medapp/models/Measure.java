package com.example.ehab.medapp.models;

public class Measure {
    String date, type,measure,comment;

    public Measure( String type, String measure, String comment,String date) {
        this.date = date;
        this.type = type;
        this.measure = measure;
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public String getMeasure() {
        return measure;
    }

    public String getComment() {
        return comment;
    }
}
