package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class ActorActivity extends AppCompatActivity {
    Button mButtonAdd;
    Button mButtonDelete;
    Button mButtonAllActors;
    Button mButtonUpdateActors;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actor);

        mButtonAdd = findViewById(R.id.button_add_actor);
        mButtonDelete = findViewById(R.id.button_delete_actor);
        mButtonAllActors = (Button) findViewById(R.id.button_see_all_actors);
        mButtonUpdateActors = (Button) findViewById(R.id.button_update_actor);

        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActorActivity.this, AddActorActivity.class);
                startActivity(intent);
            }
        });
        mButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActorActivity.this, DeleteActorActivity.class);
                startActivity(intent);
            }
        });
        mButtonAllActors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActorActivity.this, AllActorsActivity.class);
                startActivity(intent);
            }
        });
        mButtonUpdateActors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActorActivity.this, UpdateActorsActivity.class);
                startActivity(intent);
            }
        });
    }
}
