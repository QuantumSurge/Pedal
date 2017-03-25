package com.example.pratyushsharma.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

public class AddBike extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bike);

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

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout( (int) (width*.9) , (int) (height*.7) );


    }

}
