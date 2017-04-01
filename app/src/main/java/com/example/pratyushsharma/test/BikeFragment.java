package com.example.pratyushsharma.test;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.sax.RootElement;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Vikramaditya Patil on 12-03-2017.
 */

public class BikeFragment extends Fragment {

    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mFirebaseDatabase;
    private FloatingActionButton filter;
    private String[] hostels;
    private boolean[] checkeditems;
    private ArrayList<Integer> mUserItems;
    private String hostel_list;
    public String[] selectedhostels;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.activity_bike, container, false);
        final ArrayList<Bike> bikeList = new ArrayList<>();
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("Cycle");
        filter = (FloatingActionButton) rootView.findViewById(R.id.filter_btn);
        hostels = getResources().getStringArray(R.array.hostelNames);
        checkeditems = new boolean[hostels.length];
        mUserItems = new ArrayList<>();


        final BikeAdapter<Bike> bikeAdapter = new BikeAdapter(getActivity(), bikeList);
        ListView listView = (ListView) rootView.findViewById(R.id.list_view);
        listView.setAdapter(bikeAdapter);
        final Intent basic = new Intent(getContext(), BikeDetail.class);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Bike currentBike = bikeList.get(position);

                Bundle bundle = new Bundle();
                bundle.putString("uid",currentBike.getUID());
                bundle.putString("bikeName",currentBike.getBikename());
                bundle.putString("bikeAddress",currentBike.getBikeAddress());
                bundle.putInt("priceHourly",currentBike.getPrice().getHourly());
                bundle.putInt("priceDaily",currentBike.getPrice().getDaily());
                bundle.putInt("priceWeekly",currentBike.getPrice().getWeekly());
                basic.putExtras(bundle);

                startActivity(basic);

            }
        });


        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                mBuilder.setTitle("Filter by Hostels:");

                mBuilder.setCancelable(false);

                mBuilder.setMultiChoiceItems(hostels, checkeditems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if(isChecked){
                            mUserItems.add(position);
                        }else{
                            mUserItems.remove((Integer.valueOf(position)));
                        }
                    }
                });

                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String item = "";
                        for(int i = 0;i<mUserItems.size();i++){
                            item = item + hostels[mUserItems.get(i)];
                            if(i != mUserItems.size() - 1){
                                item = item + " ";
                            }
                        }
                        hostel_list = item;
                        selectedhostels = hostel_list.split("\\s+");

                        mDatabaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                                bikeList.clear();
                                while(items.hasNext()){
                                    DataSnapshot item = items.next();

                                    String address,name,uid,mboolean;
                                    int hourly , daily , weekly;

                                    address = item.child("bikeAddress").getValue().toString();
                                    name = item.child("bikename").getValue().toString();
                                    uid = item.child("uid").getValue().toString();

                                    hourly = Integer.parseInt(item.child("price").child("hourly").getValue().toString());
                                    daily = Integer.parseInt(item.child("price").child("daily").getValue().toString());
                                    weekly =Integer.parseInt(item.child("price").child("weekly").getValue().toString());

                                    mboolean = item.child("boolean").getValue().toString();

                                    Price rate = new Price(hourly,daily,weekly);
                                    Bike value = new Bike(name,address,uid,mboolean,rate);

                                    String [] hostel = address.split("\\s+");

                                    if(hostel_list.equals("")){
                                        if(mboolean.equals("true")){
                                            bikeList.add(value);
                                        }
                                    }
                                    else{
                                        for(int i=0; i<selectedhostels.length;i++){
                                            if(hostel[0].equals(selectedhostels[i])){
                                                if(mboolean.equals("true")){
                                                    bikeList.add(value);
                                                }
                                            }
                                        }
                                    }

                                }

                                bikeAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                });


                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                bikeList.clear();
                while(items.hasNext()){
                    DataSnapshot item = items.next();

                    String address,name,uid,mboolean;
                    int hourly , daily , weekly;

                    address = item.child("bikeAddress").getValue().toString();
                    name = item.child("bikename").getValue().toString();
                    uid = item.child("uid").getValue().toString();

                    hourly = Integer.parseInt(item.child("price").child("hourly").getValue().toString());
                    daily = Integer.parseInt(item.child("price").child("daily").getValue().toString());
                    weekly =Integer.parseInt(item.child("price").child("weekly").getValue().toString());

                    mboolean = item.child("boolean").getValue().toString();

                    Price rate = new Price(hourly,daily,weekly);
                    Bike value = new Bike(name,address,uid,mboolean,rate);

                    if(mboolean.equals("true")){
                        bikeList.add(value);
                    }

                }

                bikeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return rootView;
    }
}
