package com.example.myapplication.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.example.myapplication.R;
import com.example.myapplication.dbinterface.Genres;
import com.example.myapplication.dbinterface.Movies;

import java.util.ArrayList;

public class AllMoviesActivity extends AppCompatActivity {
    Movies db;
    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_movies);

        db = new Movies(this);
        listView = (ListView) findViewById(R.id.listview_all_movies);

        ArrayList<String> allMovies = db.extractMovieInformation();
        if (allMovies != null) {
            ArrayAdapter<String> itemsAdapter =
                    new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, allMovies);

            listView.setAdapter(itemsAdapter);
        } else {
            Toast.makeText(this, "No genres in database!", Toast.LENGTH_SHORT).show();
        }

    }
}