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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SetPasswordActivity extends AppCompatActivity {

    private EditText password1;
    private EditText password2;
    private Button savePasswordButton;
    private TextView errorMesage;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        password1 = (EditText) findViewById(R.id.password1);
        password2 = (EditText) findViewById(R.id.password2);
        savePasswordButton = (Button) findViewById(R.id.savePasswordButton);
        errorMesage = (TextView) findViewById(R.id.errorMessage);

        final SharedPreferences sharedPreferences = this.getSharedPreferences("com.mysafe", Context
                .MODE_PRIVATE);

        savePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredPassword1 = password1.getText().toString().trim();
                String enteredPassword2 = password2.getText().toString().trim();

                if(!enteredPassword1.isEmpty() && !enteredPassword2.isEmpty()){
                    if(enteredPassword1.equals(enteredPassword2)) {

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("password", enteredPassword1);
                        editor.apply();
                        startActivity(new Intent(SetPasswordActivity.this, MainActivity.class));

                    } else{
                        errorMesage.setText("Passwords do not match!");
                    }
                } else if(enteredPassword1.isEmpty()){
                    errorMesage.setText("Password cannot be empty!");
                } else{
                    errorMesage.setText("Passwords do not match!");
                }
            }
        });

    }

}
