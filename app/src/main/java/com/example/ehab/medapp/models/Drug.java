package com.example.ehab.medapp.models;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Drug {

    String name,dose, time,schedule,reminder;
    boolean taken;
    ArrayList<String> days;


    public Drug() {
    }

    public Drug(String name, String dose, String time, String schedule, boolean taken, ArrayList<String> days) {
        this.days =new ArrayList<>();
        this.name = name;
        this.dose = dose;
        this.time = time;
        this.schedule = schedule;
        this.taken = taken;
        this.days = days;
    }

    public Drug( String name, String dose, String time, String schedule, boolean taken) {
        this.name = name;
        this.dose = dose;
        this.time = time;
        this.schedule = schedule;
        this.taken = taken;

    }

    public Drug( String name, String dose, String time, String schedule) {
        this.name = name;
        this.dose = dose;
        this.time = time;
        this.schedule = schedule;
    }


    public boolean isTaken() {
        return taken;
    }



    public String getName() {
        return name;
    }

    public String getDose() {
        return dose;
    }

    public String getTime() {
        return time;
    }

    public String getSchedule() {
        return schedule;
    }

    public ArrayList<String> getDays() {
        return days;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    public void setDays(ArrayList<String> days) {
        this.days = days;
    }

    public String getReminder() {
        return reminder;
    }


}
