package com.example.ehab.medapp.models;

public class User {
    String id,name,email,password,age;

    public User(String name, String email, String password, String age) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
    }

    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
