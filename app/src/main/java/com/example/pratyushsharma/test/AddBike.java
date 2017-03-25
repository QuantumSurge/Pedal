package com.example.pratyushsharma.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;




public class AddBike extends AppCompatActivity {

    Spinner addressSpinner;
    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bike);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout( (int) (width*.9) , (int) (height*.7) );

        addressSpinner  = (Spinner) findViewById(R.id.address_spinner);
        adapter = ArrayAdapter.createFromResource(this,R.array.hostelNames,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addressSpinner.setAdapter(adapter);
        addressSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(),parent.getItemAtPosition(position)+" selected",Toast.LENGTH_LONG);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getBaseContext(),"Please select your hostel",Toast.LENGTH_LONG);
            }
        });

        NumberPicker dailyPicker = (NumberPicker) findViewById(R.id.dailyPicker);
        dailyPicker.setMaxValue(30);
        dailyPicker.setMinValue(0);
        dailyPicker.setWrapSelectorWheel(true);

        NumberPicker hourlyPicker = (NumberPicker) findViewById(R.id.hourlyPicker);
        hourlyPicker.setMaxValue(30);
        hourlyPicker.setMinValue(0);
        hourlyPicker.setWrapSelectorWheel(true);

        NumberPicker weeklyPicker = (NumberPicker) findViewById(R.id.weeklyPicker);
        weeklyPicker.setMaxValue(30);
        weeklyPicker.setMinValue(0);
        weeklyPicker.setWrapSelectorWheel(true);

        ImageView bikeImage = (ImageView) findViewById(R.id.bike_Img);
        bikeImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Import image code goes here
                Toast.makeText(getBaseContext(),"upload image",Toast.LENGTH_LONG);
            }
        });

    }

}
