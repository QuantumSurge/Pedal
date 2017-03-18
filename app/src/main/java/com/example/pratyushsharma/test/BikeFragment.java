package com.example.pratyushsharma.test;


import android.content.Intent;
import android.sax.RootElement;
import android.support.v4.app.Fragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Vikramaditya Patil on 12-03-2017.
 */

public class BikeFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_bike, container, false);
        ArrayList<Bike> bikeList = new ArrayList<>();
        bikeList.add(new Bike("15R", "15A401", R.drawable.icon));
        bikeList.add(new Bike("16R", "15A402", R.drawable.icon));
        bikeList.add(new Bike("17R", "15A403", R.drawable.icon));
        bikeList.add(new Bike("18R", "15A404", R.drawable.icon));
        bikeList.add(new Bike("19R", "15A405", R.drawable.icon));
        bikeList.add(new Bike("20R", "15A406", R.drawable.icon));
        bikeList.add(new Bike("15R", "15A407", R.drawable.icon));
        bikeList.add(new Bike("16R", "15A408", R.drawable.icon));
        bikeList.add(new Bike("17R", "15A409", R.drawable.icon));
        bikeList.add(new Bike("18R", "15A410", R.drawable.icon));

        BikeAdapter<Bike> bikeAdapter = new BikeAdapter(getActivity(), bikeList);
        ListView listView = (ListView) rootView.findViewById(R.id.list_view);
        listView.setAdapter(bikeAdapter);
        final Intent basic = new Intent(getContext(), BikeDetail.class);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(basic);
            }
        });
        return rootView;
    }
}
