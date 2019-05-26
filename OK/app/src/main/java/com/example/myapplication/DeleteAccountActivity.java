package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DeleteAccountActivity extends AppCompatActivity {
    Users db;
    EditText mEditTextDeleteAccount;
    Button mButtonDeleteAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        db = new Users(this);

        mEditTextDeleteAccount = (EditText) findViewById(R.id.delete_email);
        mButtonDeleteAccount = (Button) findViewById(R.id.button_delete);

        mButtonDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(db.delete_user(mEditTextDeleteAccount.getText().toString())) {
                    Intent backToLogin = new Intent(DeleteAccountActivity.this, MainActivity.class);
                    startActivity(backToLogin);
                } else {
                    Snackbar.make(mButtonDeleteAccount, "Your account was not deleted! Please, try again!", Snackbar.LENGTH_LONG).show();

                }
            }
        });
    }
}
