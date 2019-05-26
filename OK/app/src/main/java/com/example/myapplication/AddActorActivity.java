package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActorActivity extends AppCompatActivity {
    Actors db;
    EditText mEditTextActorName;
    EditText mEditTextActorAge;
    Button mButtonAddGenre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_actor);

        db = new Actors(this);
        mEditTextActorName = (EditText) findViewById(R.id.add_actor_name);
        mEditTextActorAge = (EditText) findViewById(R.id.add_actor_age);
        mButtonAddGenre = (Button) findViewById(R.id.button_add_actor);

        mButtonAddGenre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEditTextActorName.getText().toString();
                String s_age = mEditTextActorAge.getText().toString();
                int age = 0;
                try {
                    age = Integer.parseInt(s_age);
                } catch(NumberFormatException e) {
                    age = -2;
                }
                Actor actor = new Actor(name, age);
                if(db.add(actor)) {
                    Intent intent = new Intent(AddActorActivity.this, ActorActivity.class);
                    startActivity(intent);
                } else {
                    Snackbar.make(mButtonAddGenre, "Actor already exists in database!", Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }
}