package com.example.pratyushsharma.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.nio.BufferUnderflowException;

/**
 * Created by Vikramaditya Patil on 17-03-2017.
 */

public class BikeDetail extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bike_detail);

        Bundle bundle = getIntent().getExtras();
        String uId = bundle.getString("uId");
        String bikeName = bundle.getString("bikeName");
        String bikeAddress = bundle.getString("bikeAddress");
        int priceHourly = bundle.getInt("priceHourly");
        int priceDaily = bundle.getInt("priceDaily");
        int priceWeekly = bundle.getInt("priceWeekly");

        TextView bikeNameView = (TextView) findViewById(R.id.bike_name);
        bikeNameView.setText(bikeName);

        TextView hourPriceView = (TextView) findViewById(R.id.mHourlyPrice);
        hourPriceView.setText(String.valueOf(priceHourly));

        TextView dayPriceView = (TextView) findViewById(R.id.mDailyPrice);
        dayPriceView.setText(String.valueOf(priceDaily));

        TextView weekPriceView = (TextView) findViewById(R.id.mWeeklyPrice);
        weekPriceView.setText(String.valueOf(priceWeekly));

        TextView bikeAdressView = (TextView) findViewById(R.id.bikeAddress);
        bikeAdressView.setText(bikeAddress);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout( (int) (width*.9) , (int) (height*.7) );

    }
}
