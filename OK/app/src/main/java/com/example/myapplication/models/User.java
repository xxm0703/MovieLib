package com.example.myapplication.models;

public class User {
    public String email;
    public String password;
    public String name;

    public User(String email, String password, String name) {

        this.email = email;
        this.password = password;
        this.name = name;
    }

    public User(String email) {

        this.email = email;
    }
}
