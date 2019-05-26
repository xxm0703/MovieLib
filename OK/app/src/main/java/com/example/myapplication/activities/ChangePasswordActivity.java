package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;
import com.example.myapplication.dbinterface.Users;

public class ChangePasswordActivity extends AppCompatActivity {
    Users db;
    EditText mEditTextChangeEmail;
    EditText mEditTextChangeName;
    EditText mEditTextChangePassword;
    Button mButtonUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        db = new Users(this);

        mEditTextChangeEmail = (EditText) findViewById(R.id.change_email);
        mEditTextChangeName = (EditText) findViewById(R.id.change_name);
        mEditTextChangePassword = (EditText) findViewById(R.id.change_password);
        mButtonUpdate = (Button) findViewById(R.id.button_change_information);

        mButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEditTextChangeEmail.getText().toString();
                String password = mEditTextChangePassword.getText().toString();
                String name = mEditTextChangeName.getText().toString();
                boolean updated = db.update_information(email, password);

                if(updated) {
                    Intent toHome = new Intent(ChangePasswordActivity.this, MainActivity.class);
                    startActivity(toHome);
                } else {
                    Snackbar.make(mButtonUpdate, "Password was not changed! Please, try again!", Snackbar.LENGTH_LONG).show();
                }

            }
        });
    }

}
