package com.example.myapplication.models;

public class MoviesActorsEntry {

    private int movieId;

    private int actorId;

    public MoviesActorsEntry(int movieId, int actorId) {
        this.movieId = movieId;
        this.actorId = actorId;
    }

    public MoviesActorsEntry(Movie movie, Actor actor) {
        this(movie.getId(), actor.getId());
    }

    public int getMovieId() {
        return movieId;
    }

    public int getActorId() {
        return actorId;
    }
}
