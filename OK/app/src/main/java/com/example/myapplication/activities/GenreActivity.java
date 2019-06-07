package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class GenreActivity extends AppCompatActivity {
    Button mButtonAdd;
    Button mButtonDelete;
    Button mButtonSeeAll;
    Button mButtonUpdate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre);

        mButtonAdd = findViewById(R.id.button_add_genre);
        mButtonDelete = findViewById(R.id.button_delete_genre);
        mButtonSeeAll = findViewById(R.id.button_see_all_genres);
        mButtonUpdate = (Button) findViewById(R.id.button_update_genre);

        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GenreActivity.this, AddGenreActivity.class);
                startActivity(intent);
            }
        });
        mButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GenreActivity.this, DeleteGenreActivity.class);
                startActivity(intent);
            }
        });
        mButtonSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GenreActivity.this, AllGenresActivity.class);
                startActivity(intent);
            }
        });
        mButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GenreActivity.this, UpdateGenresActivity.class);
                startActivity(intent);
            }
        });
    }
}
