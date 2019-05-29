package com.example.myapplication.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.example.myapplication.R;
import com.example.myapplication.dbinterface.Genres;

import java.util.ArrayList;

public class AllGenresActivity extends AppCompatActivity {
    Genres db;
    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_genres);

        db = new Genres(this);
        listView = (ListView) findViewById(R.id.listview_all_genres);

        ArrayList<String> all_genres = db.extractGenresInformation();
        if (all_genres != null) {
            ArrayAdapter<String> itemsAdapter =
                    new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, all_genres);

            listView.setAdapter(itemsAdapter);
        } else {
            Toast.makeText(this, "No genres in database!", Toast.LENGTH_SHORT).show();
        }

    }
}