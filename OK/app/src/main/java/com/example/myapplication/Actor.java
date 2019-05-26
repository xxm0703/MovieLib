package com.example.myapplication;

public class Actor {

    private int id;

    private String name;

    private int age;

    public Actor(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Actor(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String setName(String name) {
        return this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
