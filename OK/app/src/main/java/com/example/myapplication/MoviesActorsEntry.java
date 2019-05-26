package com.example.myapplication;

public class MoviesActorsEntry {

    private int id;

    private int movieId;

    private int actorId;

    public MoviesActorsEntry(int id, int movieId, int actorId) {
        this.id = id;
        this.movieId = movieId;
        this.actorId = actorId;
    }

    public MoviesActorsEntry(int id, Movie movie, Actor actor) {
        this(id, movie.getId(), actor.getId());
    }

    public MoviesActorsEntry(int movieId, int actorId) {
        this.movieId = movieId;
        this.actorId = actorId;
    }

    public MoviesActorsEntry(Movie movie, Actor actor) {
        this(movie.getId(), actor.getId());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieId() {
        return movieId;
    }

    public int getActorId() {
        return actorId;
    }
}
