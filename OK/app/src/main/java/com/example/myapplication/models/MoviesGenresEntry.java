package com.example.myapplication.models;

public class MoviesGenresEntry {

    private int movieId;

    private int genreId;

    MoviesGenresEntry(int movieId, int genreId) {
        this.movieId = movieId;
        this.genreId = genreId;
    }

    public MoviesGenresEntry(Movie movie, Genre genre) {
        this(movie.getId(), genre.getId());
    }

    public int getMovieId() {
        return movieId;
    }

    public int getGenreId() {
        return genreId;
    }

}


