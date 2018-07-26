package com.example.ehab.medapp.models;

import java.util.ArrayList;

public class DayPart {
    String name;
    ArrayList<Drug> drugs;

    public DayPart(String name, ArrayList<Drug> drugs) {
        this.name = name;
        this.drugs = drugs;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Drug> getDrugs() {
        return drugs;
    }
}
