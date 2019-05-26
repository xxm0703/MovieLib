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

public class DeleteGenreActivity extends AppCompatActivity {
    Genres db;
    Button mButtonDeleteGenre;
    EditText mEditTextGenre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_genre);

        db = new Genres(this);
        mButtonDeleteGenre = (Button) findViewById(R.id.button_delete_genre1);
        mEditTextGenre = (EditText) findViewById(R.id.delete_genre);

        mButtonDeleteGenre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Genre genre = db.findByName(mEditTextGenre.getText().toString());
                if(db.delete(genre)) {
                    Intent intent = new Intent(DeleteGenreActivity.this, GenreActivity.class);
                    startActivity(intent);
                } else {
                    Snackbar.make(mButtonDeleteGenre, "No such genre!", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}
