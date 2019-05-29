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

public class UpdateMovieActivity extends AppCompatActivity {
    Movies db;
    EditText mEditTextName;
    EditText mEditTextReleaseDate;
    EditText mEditTextNewName;
    EditText mEditTextNewReleaseDate;
    Button mButtonUpdateMovie;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_movie);
        db = new Movies(this);
        mEditTextName = (EditText) findViewById(R.id.movie_name);
        mEditTextReleaseDate = (EditText) findViewById(R.id.movie_date);
        mEditTextNewName = (EditText) findViewById(R.id.new_movie_name);
        mEditTextNewReleaseDate = (EditText) findViewById(R.id.new_movie_date);
        mButtonUpdateMovie = (Button) findViewById(R.id.button_update_movie);

        mButtonUpdateMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy");

                String name = mEditTextName.getText().toString().trim();
                String date = mEditTextReleaseDate.getText().toString().trim();
                String newName = mEditTextNewName.getText().toString().trim();
                String newDate = mEditTextNewReleaseDate.getText().toString().trim();

                Date newReleaseDate = null;
                try {
                    newReleaseDate = formatter.parse(newDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Movie movie = db.findByName(name);
                if(movie!=null) {
                    if(db.update(movie, newName, newReleaseDate)) {
                        Intent intent = new Intent(UpdateMovieActivity.this, MovieActivity.class);
                        startActivity(intent);
                    } else {
                        Snackbar.make(mButtonUpdateMovie, "No such movie!", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(mButtonUpdateMovie, "Movie does not exist!", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}
