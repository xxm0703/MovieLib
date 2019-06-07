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

public class UpdateGenresActivity extends AppCompatActivity {
    Genres db;
    EditText mEditTextGenreOldName;
    EditText mEditTextGenreNewName;
    Button mButtonUpdateGenre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_genres);

        db = new Genres(this);
        mEditTextGenreOldName = (EditText) findViewById(R.id.genre_old_name);
        mEditTextGenreNewName = (EditText) findViewById(R.id.genre_new_name);
        mButtonUpdateGenre = (Button) findViewById(R.id.button_update_genre1);


        mButtonUpdateGenre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Genre genre = db.findByName(mEditTextGenreOldName.getText().toString().trim());
                if(genre != null) {
                    if (db.update(mEditTextGenreNewName.getText().toString().trim(), genre)) {
                        Intent intent = new Intent(UpdateGenresActivity.this, GenreActivity.class);
                        startActivity(intent);
                    } else {
                        Snackbar.make(mButtonUpdateGenre, "Something went wrong!", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(mButtonUpdateGenre, "No such genre!", Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }
}
