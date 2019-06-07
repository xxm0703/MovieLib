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
import com.example.myapplication.models.User;

public class ChangePasswordActivity extends AppCompatActivity {
    Users db;
    EditText mEditTextChangeEmail;
    EditText mEditTextChangePassword;
    EditText mEditTextChangeName;
    Button mButtonUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        db = new Users(this);

        mEditTextChangeEmail = findViewById(R.id.change_email);
        mEditTextChangePassword = findViewById(R.id.change_password);
        mEditTextChangeName = findViewById(R.id.name);
        mButtonUpdate = findViewById(R.id.button_change_information);

        mButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEditTextChangeEmail.getText().toString();
                String name = mEditTextChangeName.getText().toString();
                String password = mEditTextChangePassword.getText().toString();
                User user = db.findByEmail(email);

                if(user != null) {
                    boolean updated = db.update_information(email, password, user);

                    if (updated && password == user.getPassword()) {
                        Intent toHome = new Intent(ChangePasswordActivity.this, MainActivity.class);
                        startActivity(toHome);
                    } else {
                        Snackbar.make(mButtonUpdate, "Password was not changed! Please, try again!", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(mButtonUpdate, "No such person!", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

}
