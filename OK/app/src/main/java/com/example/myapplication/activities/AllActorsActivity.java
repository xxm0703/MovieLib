package com.example.myapplication.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.example.myapplication.R;
import com.example.myapplication.dbinterface.Actors;

import java.util.ArrayList;

public class AllActorsActivity extends AppCompatActivity {
    Actors db;
    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_actors);

        db = new Actors(this);
        listView = (ListView) findViewById(R.id.listview_all_actors);

        ArrayList<String> all_actors = db.extractActorsInformation();
        if (all_actors != null) {
            ArrayAdapter<String> itemsAdapter =
                    new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, all_actors);

            listView.setAdapter(itemsAdapter);
        } else {
            Toast.makeText(this, "No actores in database!", Toast.LENGTH_SHORT).show();
        }

    }
}