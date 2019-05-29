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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actor);

        mButtonAdd = findViewById(R.id.button_add_actor);
        mButtonDelete = findViewById(R.id.button_delete_actor);

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
    }
}
