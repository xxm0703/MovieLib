package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class MovieActivity extends AppCompatActivity {
    Button mButtonAdd;
    Button mButtonDelete;
    Button mButtonSeeAll;
    Button mButtonUpdate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        mButtonAdd = findViewById(R.id.button_add_movie);
        mButtonDelete = findViewById(R.id.button_delete_movie);
        mButtonSeeAll = findViewById(R.id.button_see_all_movies);
        mButtonUpdate = (Button) findViewById(R.id.button_update_movie);

        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieActivity.this, AddMovieActivity.class);
                startActivity(intent);
            }
        });
        mButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieActivity.this, DeleteMovieActivity.class);
                startActivity(intent);
            }
        });
        mButtonSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieActivity.this, AllMoviesActivity.class);
                startActivity(intent);
            }
        });
        mButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieActivity.this, UpdateMovieActivity.class);
                startActivity(intent);
            }
        });
    }
}
