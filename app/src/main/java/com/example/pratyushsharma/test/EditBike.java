package com.example.pratyushsharma.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import static android.os.Build.VERSION_CODES.M;
import static com.example.pratyushsharma.test.R.id.dailyPicker;
import static com.example.pratyushsharma.test.R.id.hourlyPicker;
import static com.example.pratyushsharma.test.R.id.weeklyPicker;
import static java.security.AccessController.getContext;

public class EditBike extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bike);
        Bundle bundle = getIntent().getExtras();
        String[] bikeAdd = bundle.getStringArray("Bike Address");
        String bikeName = bundle.getString("Bike Name");
        String hourPrice = bundle.getString("Hour Price");
        String dayPrice = bundle.getString("Day Price");
        String weekPrice = bundle.getString("Week Price");

        Spinner hourSpinner = (Spinner) findViewById(R.id.address_spinner);
        NumberPicker hourPicker = (NumberPicker) findViewById(hourlyPicker);
        hourPicker.setMaxValue(getBaseContext().getResources().getInteger(R.integer.maxPriceForHour));
        hourPicker.setMinValue(getBaseContext().getResources().getInteger(R.integer.minPriceForHour));
        hourPicker.setWrapSelectorWheel(true);
        NumberPicker dayPicker = (NumberPicker) findViewById(dailyPicker);
        dayPicker.setMaxValue(getBaseContext().getResources().getInteger(R.integer.maxPriceForDay));
        dayPicker.setMinValue(getBaseContext().getResources().getInteger(R.integer.minPriceForDay));
        dayPicker.setWrapSelectorWheel(true);
        NumberPicker weekPicker = (NumberPicker) findViewById(weeklyPicker);
        weekPicker.setMaxValue(getBaseContext().getResources().getInteger(R.integer.maxPriceForWeek));
        weekPicker.setMinValue(getBaseContext().getResources().getInteger(R.integer.minPriceForWeek));
        weekPicker.setWrapSelectorWheel(true);

        EditText mBikeAdd = (EditText) findViewById(R.id.room);
        EditText mBikeName = (EditText) findViewById(R.id.bike_name);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.hostelNames,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hourSpinner.setAdapter(adapter);

        mBikeAdd.setText(bikeAdd[1]);
        mBikeName.setText(bikeName);
        hourSpinner.setSelection(adapter.getPosition(bikeAdd[0]));
        hourPicker.setValue(Integer.valueOf(hourPrice));
        dayPicker.setValue(Integer.valueOf(dayPrice));
        weekPicker.setValue(Integer.valueOf(weekPrice));

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout( (int) (width*.9) , (int) (height*.7) );


    }
}
