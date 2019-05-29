package com.example.myapplication.models;

public class MoviesGenresEntry {
    private int id;

    private int movieId;

    private int genreId;

    MoviesGenresEntry(int id, int movieId, int genreId) {
        this.id = id;
        this.movieId = movieId;
        this.genreId = genreId;
    }

    public MoviesGenresEntry(int id, Movie movie, Genre genre) {
        this(id, movie.getId(), genre.getId());
    }

    MoviesGenresEntry(int movieId, int genreId) {
        this.movieId = movieId;
        this.genreId = genreId;
    }

    public MoviesGenresEntry(Movie movie, Genre genre) {
        this(movie.getId(), genre.getId());
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

    public int getGenreId() {
        return genreId;
    }

}


