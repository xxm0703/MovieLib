package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {
    Users db;
    EditText mTextEmail;
    EditText mTextPassword;
    EditText mTextConfirmPassword;
    EditText mTextName;
    Button mButtonRegister;
    TextView mTextViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new Users(this);
        mTextEmail = (EditText) findViewById(R.id.email);
        mTextName = (EditText) findViewById(R.id.name);
        mTextPassword = (EditText) findViewById(R.id.password);
        mTextConfirmPassword = (EditText) findViewById(R.id.confrim_password);
        mButtonRegister = (Button) findViewById(R.id.button_register);
        mTextViewLogin = (TextView) findViewById(R.id.textview_login);

        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginIntent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(LoginIntent);
            }
        });

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    String Email = mTextEmail.getText().toString();
                    String Password = mTextPassword.getText().toString();
                    String Confirm_Password = mTextConfirmPassword.getText().toString();
                    String Name = mTextName.getText().toString();

                    if (!db.isEmailExists(Email)) {
                        db.addUser(new User(Email, Password, Name));
                        Snackbar.make(mButtonRegister, "User created successfully! Please Login!", Snackbar.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, Snackbar.LENGTH_LONG);
                    }else {
                        Snackbar.make(mButtonRegister, "User already exists!", Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
    public boolean validate() {
        boolean valid = true;

        String Email = mTextEmail.getText().toString();
        String Password = mTextPassword.getText().toString();
        String Confirm_Password = mTextConfirmPassword.getText().toString();

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
                mTextPassword.setError("Password is to short! Enter at least 5 symbols!");
            }
        }

        if(!Password.equals(Confirm_Password)) {
            valid = false;
            mTextPassword.setError("Passwords does not match!");
        }

        return valid;
    }
}
