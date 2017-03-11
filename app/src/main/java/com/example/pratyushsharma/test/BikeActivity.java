package com.example.pratyushsharma.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

        BikeAdapter<Bike> bikeAdapter = new BikeAdapter(this, bikeList);
        final Intent basic = new Intent(this, MainActivity.class);

        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(bikeAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(basic);

            }
        });
    }
}
