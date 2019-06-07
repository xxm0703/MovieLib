package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.dbinterface.Actors;
import com.example.myapplication.dbinterface.Movies;
import com.example.myapplication.dbinterface.MoviesActors;
import com.example.myapplication.models.Actor;
import com.example.myapplication.models.Movie;
import com.example.myapplication.models.MoviesActorsEntry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AddMovieActivity extends AppCompatActivity {
    Movies db;
    Actors actorDataBase;
    MoviesActors movieActorsDataBase;
    EditText mEditTextName;
    EditText mEditTextReleaseDate;
    EditText mEditTextMovieActors;
    EditText mEditTextMovieGenres;
    Button mButtonAddMovie;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);
        db = new Movies(this);
        actorDataBase = new Actors(this);
        movieActorsDataBase = new MoviesActors(this);
        mEditTextName = (EditText) findViewById(R.id.add_movie_name);
        mEditTextReleaseDate = (EditText) findViewById(R.id.add_movie_date);
        mEditTextMovieActors = (EditText) findViewById(R.id.add_movie_actors);
        mEditTextMovieGenres = (EditText) findViewById(R.id.add_movie_genres);
        mButtonAddMovie = (Button) findViewById(R.id.button_add_movie);

        mButtonAddMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy");

                String name = mEditTextName.getText().toString();
                String date = mEditTextReleaseDate.getText().toString();
                Date releaseDate = null;
                try {
                    releaseDate = formatter.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Movie movie = new Movie(name, releaseDate);

                if(db.add(movie) &&  validateActors(movie)) {
                    Intent intent = new Intent(AddMovieActivity.this, MovieActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(AddMovieActivity.this,"Something went wrong! /A movie like that already exists!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public boolean validateActors(Movie movie) {
        String[] actors = mEditTextMovieActors.getText().toString().split(",");
        for(String actorName : actors) {
            Actor actor = new Actor(actorName);
            if(!actorDataBase.exists(actor)) {
                mEditTextMovieActors.setError("One of the actors is not registered in the database!");
                break;
            } else {
                MoviesActorsEntry movieEntry = new MoviesActorsEntry(movie.getId(), actor.getId());
                if(movieActorsDataBase.add(movieEntry)) {
                    return true;
                }
            }
        }
        return false;
    }
}

