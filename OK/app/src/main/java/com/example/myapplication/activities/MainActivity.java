package com.example.myapplication.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.dbinterface.Users;
import com.example.myapplication.models.User;

public class MainActivity extends AppCompatActivity {

    Users db;
    EditText mTextEmail;
    EditText mTextPassword;
    Button mButtonLogin;
    TextView mTextViewRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new Users(this);
        mTextEmail = findViewById(R.id.email);
        mTextPassword = findViewById(R.id.password);
        mButtonLogin = findViewById(R.id.button_login);
        mTextViewRegister = findViewById(R.id.textview_register);

        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    String Email = mTextEmail.getText().toString();

                    User currentUser = db.findByEmail(Email);
                    if (currentUser != null) {
                        Snackbar.make(mButtonLogin, "Successfully Logged In!", Snackbar.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                    } else {
                        Snackbar.make(mButtonLogin, "Login Error! Please Try Again!", Snackbar.LENGTH_LONG).show();

                    }
                }
            }

        });
    }

    public boolean validate() {
        boolean valid = true;
        String Email = mTextEmail.getText().toString();
        String Password = mTextPassword.getText().toString();

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            valid = false;
            mTextEmail.setError("Please enter valid email!");
        } else {
            mTextEmail.setError(null);
        }

        if (Password.isEmpty()) {
            valid = false;
            mTextPassword.setError("Please enter valid password!");
        } else {
            if (Password.length() > 5) {
                mTextPassword.setError(null);
            } else {
                valid = false;
                mTextPassword.setError("Password is to short!");
            }
        }
        return valid;
    }
}
