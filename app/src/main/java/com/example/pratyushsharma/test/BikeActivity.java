package com.example.pratyushsharma.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class BikeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike);
        ArrayList<Bike>  bikeList = new ArrayList<>();
        bikeList.add(new Bike("15R","15A401",R.drawable.icon));
        bikeList.add(new Bike("16R","15A402",R.drawable.icon));
        bikeList.add(new Bike("17R","15A403",R.drawable.icon));
        bikeList.add(new Bike("18R","15A404",R.drawable.icon));
        bikeList.add(new Bike("19R","15A405",R.drawable.icon));
        bikeList.add(new Bike("20R","15A406",R.drawable.icon));
        bikeList.add(new Bike("15R","15A407",R.drawable.icon));
        bikeList.add(new Bike("16R","15A408",R.drawable.icon));
        bikeList.add(new Bike("17R","15A409",R.drawable.icon));
        bikeList.add(new Bike("18R","15A410",R.drawable.icon));

        ArrayAdapter<Bike> bikeAdapter = new ArrayAdapter<Bike>();

    }
}
