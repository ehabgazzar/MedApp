package com.example.ehab.medapp.models;

public class Drug {

    String id,name,dose,dateTime,description;
    boolean taken;


    public Drug(String id, String name, String dose, String dateTime, String description, boolean taken) {
        this.id = id;
        this.name = name;
        this.dose = dose;
        this.dateTime = dateTime;
        this.description = description;
        this.taken = taken;
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

    public String getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }
}
