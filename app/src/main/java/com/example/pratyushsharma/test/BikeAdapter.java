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

        TextView priceTextView = (TextView) listItemView.findViewById(R.id.bike_Price);
        priceTextView.setText(String.valueOf(currentBike.getPrice().getDaily())+"-"+String.valueOf(currentBike.getPrice().getHourly())+"-"+String.valueOf(currentBike.getPrice().getWeekly()));

        //ImageView iconView = (ImageView) listItemView.findViewById(R.id.bike_Img);
        //iconView.setImageResource(currentBike.getBikeId());

        return listItemView;
    }

}

