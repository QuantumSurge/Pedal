package com.example.pratyushsharma.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CurrentRide extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_ride);
        Button endRideButton = (Button) findViewById(R.id.end_ride);
        endRideButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CurrentRide.this, Mainpage.class));
            }
        });
    }
}
