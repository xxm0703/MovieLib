package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;
import com.example.myapplication.dbinterface.Actors;
import com.example.myapplication.models.Actor;

public class UpdateActorsActivity extends AppCompatActivity {
    Actors db;
    EditText mEditTextActorName;
    EditText mEditTextActorAge;
    EditText mEditTextNewActorName;
    EditText mEditTextNewActorAge;
    Button mButtonUpdateGenre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivty_update_actors);

        db = new Actors(this);
        mEditTextActorName = (EditText)findViewById(R.id.actor_old_name);
        mEditTextActorAge = (EditText)findViewById(R.id.actor_age);

        mEditTextNewActorName = (EditText)findViewById(R.id.actor_new_name);
        mEditTextNewActorAge = (EditText) findViewById(R.id.new_actor_age);
        mButtonUpdateGenre = (Button) findViewById(R.id.button_update_actor1);

        mButtonUpdateGenre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEditTextActorName.getText().toString().trim();
                String actor_age = mEditTextActorAge.getText().toString().trim();

                String new_name = mEditTextNewActorName.getText().toString();
                String new_age = mEditTextNewActorAge.getText().toString();

                int age, new_age_actor;
                try {
                    age = Integer.parseInt(actor_age);
                    new_age_actor = Integer.parseInt(new_age);
                } catch(NumberFormatException e) {
                    age = -2;
                    new_age_actor = -2;
                }
                Actor actor = db.findByName(name);
                if(actor != null) {
                    if (db.update(new_name, new_age_actor, actor)) {
                        Intent intent = new Intent(UpdateActorsActivity.this, ActorActivity.class);
                        startActivity(intent);
                    } else {
                        Snackbar.make(mButtonUpdateGenre, "Something went wrong", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(mButtonUpdateGenre, "Actor does not exist!", Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }
}