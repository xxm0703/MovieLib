package com.example.myapplication;

public class User {
    public String id;
    public String email;
    public String password;
    public String name;

    public User(String id, String email, String password, String name) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public User(String id, String email) {
        this.id = id;
        this.email = email;
    }
}
