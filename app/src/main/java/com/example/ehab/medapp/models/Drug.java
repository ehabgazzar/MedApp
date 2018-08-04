package com.example.ehab.medapp.models;

import java.util.ArrayList;

public class Drug {

    String id,name,dose, time,schedule,reminder;
    boolean taken;
    ArrayList<String> days;

    public Drug(String id, String name, String dose, String time, String schedule, boolean taken, ArrayList<String> days) {
        this.days =new ArrayList<>();
        this.id = id;
        this.name = name;
        this.dose = dose;
        this.time = time;
        this.schedule = schedule;
        this.taken = taken;
        this.days = days;
    }

    public Drug(String id, String name, String dose, String time, String schedule, boolean taken) {
        this.id = id;
        this.name = name;
        this.dose = dose;
        this.time = time;
        this.schedule = schedule;
        this.taken = taken;
    }

    public Drug(String id, String name, String dose, String time, String schedule) {
        this.id = id;
        this.name = name;
        this.dose = dose;
        this.time = time;
        this.schedule = schedule;
    }

    public boolean isTaken() {
        return taken;
    }

    public String getId() {
        return id;
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
}
