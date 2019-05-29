package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.dbinterface.Movies;
import com.example.myapplication.models.Movie;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddMovieActivity extends AppCompatActivity {
    Movies db;
    EditText mEditTextName;
    EditText mEditTextReleaseDate;
    Button mButtonAddMovie;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);
        db = new Movies(this);
        mEditTextName = (EditText) findViewById(R.id.add_movie_name);
        mEditTextReleaseDate = (EditText) findViewById(R.id.add_movie_date);
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
                if(db.add(movie)) {
                    Intent intent = new Intent(AddMovieActivity.this, MovieActivity.class);
                    startActivity(intent);
                } else {
                    Snackbar.make(mButtonAddMovie, "Movie already exists in database!", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}
