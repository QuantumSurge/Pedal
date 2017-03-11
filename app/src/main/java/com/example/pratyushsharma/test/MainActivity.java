package com.example.pratyushsharma.test;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.app.Activity;

import static com.example.pratyushsharma.test.R.layout.activity_main;


public class MainActivity extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(activity_main);

        Button mLoginButton;
        Button mSignupButton;

        mLoginButton = (Button) findViewById(R.id.Login);
        mSignupButton = (Button) findViewById(R.id.Signup);


        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this,
                        Login.class);
                startActivity(myIntent);
            }
        });

        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this,
                        Signup.class);
                startActivity(myIntent);
            }
        });


    }




}
