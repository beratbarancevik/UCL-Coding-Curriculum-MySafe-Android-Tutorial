package com.mysafe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Set;

public class LoginActivity extends AppCompatActivity {

    private EditText password;
    private Button loginButton;
    private TextView passwordError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        password = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.loginButton);
        passwordError = (TextView) findViewById(R.id.passwordError);

        final SharedPreferences sharedPreferences = this.getSharedPreferences("com.mysafe", Context
                .MODE_PRIVATE);

        if(!sharedPreferences.contains("password")){
            startActivity(new Intent(this, SetPasswordActivity.class));
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredPassword = password.getText().toString().trim();
                String savedPassword = sharedPreferences.getString("password", null);
                if(enteredPassword.equals(savedPassword)){
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else{
                    passwordError.setText("Wrong Password!");
                }
            }
        });

    }

}
