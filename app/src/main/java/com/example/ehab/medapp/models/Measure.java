package com.example.ehab.medapp.models;

public class Measure {
    String date,time,measure,comment;

    public Measure(String date, String time, String measure, String comment) {
        this.date = date;
        this.time = time;
        this.measure = measure;
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getMeasure() {
        return measure;
    }

    public String getComment() {
        return comment;
    }
}
