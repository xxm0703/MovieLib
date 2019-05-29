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

public class DeleteActorActivity extends AppCompatActivity {
    Actors db;
    Button mButtonDeleteActor;
    EditText mEditTextActor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_actor);

        db = new Actors(this);
        mButtonDeleteActor = findViewById(R.id.button_delete_actor);
        mEditTextActor = findViewById(R.id.delete_actor);

        mButtonDeleteActor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Actor actor = db.findByName(mEditTextActor.getText().toString());
                if(db.delete(actor)) {
                    Intent intent = new Intent(DeleteActorActivity.this, ActorActivity.class);
                    startActivity(intent);
                } else {
                    Snackbar.make(mButtonDeleteActor, "No such actor!", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}
