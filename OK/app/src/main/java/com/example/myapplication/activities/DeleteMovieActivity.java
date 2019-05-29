package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;
import com.example.myapplication.dbinterface.Movies;
import com.example.myapplication.models.Movie;

public class DeleteMovieActivity extends AppCompatActivity {
    Movies db;
    EditText mEditTextName;
    Button mButtonDeleteMovie;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_movie);

        db = new Movies(this);
        mEditTextName = (EditText) findViewById(R.id.delete_movie);
        mButtonDeleteMovie = (Button) findViewById(R.id.button_delete_movie);

        mButtonDeleteMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Movie movie = db.findByName(mEditTextName.getText().toString().trim());
                if(movie == null) {
                    Snackbar.make(mButtonDeleteMovie, "No such movie!", Snackbar.LENGTH_LONG).show();

                } else {
                    if (db.delete(movie)) {
                        Intent intent = new Intent(DeleteMovieActivity.this, MovieActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }
}
