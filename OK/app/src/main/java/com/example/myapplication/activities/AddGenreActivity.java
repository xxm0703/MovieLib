package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;
import com.example.myapplication.dbinterface.Genres;
import com.example.myapplication.models.Genre;

public class AddGenreActivity extends AppCompatActivity {
    Genres db;
    EditText mEditTextGenre;
    Button mButtonAddGenre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_genre);

        db = new Genres(this);
        mEditTextGenre = findViewById(R.id.add_genre);
        mButtonAddGenre = findViewById(R.id.button_add_genre);

        mButtonAddGenre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Genre genre = new Genre(mEditTextGenre.getText().toString());
                if(db.add(genre)) {
                    Intent intent = new Intent(AddGenreActivity.this, GenreActivity.class);
                    startActivity(intent);
                } else {
                    Snackbar.make(mButtonAddGenre, "Genre already exists!", Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }
}