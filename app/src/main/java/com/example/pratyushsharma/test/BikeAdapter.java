package com.example.pratyushsharma.test;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class BikeAdapter<T> extends ArrayAdapter<Bike> {

    public BikeAdapter(Activity context, ArrayList<Bike> pBikeList) {
        super(context, 0, pBikeList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        Bike currentBike =  getItem(position);


        TextView addTextView = (TextView) listItemView.findViewById(R.id.bike_Add);
        addTextView.setText(currentBike.getBikeAddress());

        TextView bikeNameTextView = (TextView) listItemView.findViewById(R.id.bike_name);
        bikeNameTextView.setText(currentBike.getBikename());

        TextView hourPriceView = (TextView) listItemView.findViewById(R.id.hour_price);
        hourPriceView.setText(String.valueOf(currentBike.getPrice().getHourly()));

        TextView dayPriceView = (TextView) listItemView.findViewById(R.id.day_price);
        dayPriceView.setText(String.valueOf(currentBike.getPrice().getDaily()));

        TextView weekPriceView = (TextView) listItemView.findViewById(R.id.week_price);
        weekPriceView.setText(String.valueOf(currentBike.getPrice().getWeekly()));

        //ImageView iconView = (ImageView) listItemView.findViewById(R.id.bike_Img);
        //iconView.setImageResource(currentBike.getBikeId());

        return listItemView;
    }

}

