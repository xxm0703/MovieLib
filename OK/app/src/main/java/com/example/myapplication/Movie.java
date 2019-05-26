package com.example.myapplication;

import java.util.Date;

public class Movie {

    private int id;

    private String name;

    private Date releaseDate;

    public Movie(int id, String name, Date releaseDate) {
        this.id = id;
        this.name = name;
        this.releaseDate = releaseDate;
    }

    public Movie(String name, Date releaseDate) {
        this.name = name;
        this.releaseDate = releaseDate;
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

    public Date getReleaseDate() {
        return releaseDate;
    }
}
