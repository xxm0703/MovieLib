package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {
    Users db;
    Button mButtonUpdate;
    Button mButtonDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new Users(this);
        mButtonUpdate = (Button) findViewById(R.id.button_update);
        mButtonDelete = (Button) findViewById(R.id.button_delete);

        mButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changePassword = new Intent(HomeActivity.this, ChangePasswordActivity.class);
                startActivity(changePassword);
            }
        });

        mButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toLogin = new Intent(HomeActivity.this, DeleteAccountActivity.class);
                startActivity(toLogin);
            }
        });

    }
}
