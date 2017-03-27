package com.example.pratyushsharma.test;


import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.media.ImageWriter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Vector;

import static android.R.interpolator.linear;
import static android.widget.Toast.LENGTH_SHORT;


public class ProfileFragment extends Fragment{
    public  String bikeName ;
    public String bikeAddress;
    public TextView bikeNameView;
    public TextView bikeAddressView;
    public TextView priceHourView;
    public TextView priceDayView;
    public TextView priceWeekView;
    public Price price;
    public Boolean bike;
    public ImageView bikeImage;
    private ImageView profile_pic;
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mFirebaseDatabase;
    private String uid;
    private StorageReference mstorage;
    private FirebaseAuth mFirebaseAuth;

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
                             Bundle savedInstanceState) {
        final View myView = inflater.inflate(R.layout.fragment_profile, container, false);

        profile_pic = (ImageView) myView.findViewById(R.id.profile_pic);
        mFirebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mFirebaseAuth.getCurrentUser();
        uid = user.getUid();

        bike = false;

        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();

        mstorage= FirebaseStorage.getInstance().getReference();


        StorageReference storageRef = mstorage.child(uid).child("/profile.jpg");
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(profile_pic.getContext()).load(uri).fit().into(profile_pic);
            }
        });
        final LinearLayout linearLayout = (LinearLayout) myView.findViewById(R.id.myBikeCard);
        final View myBikeView = inflater.inflate(R.layout.list_item2,container,false);


        mDatabaseReference.child("Cycle").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(uid)){
                    linearLayout.removeView(myBikeView);
                    linearLayout.addView(myBikeView);
                    //Switch code for ready and non ready state switching
                    final Switch readySwitch = (Switch) myView.findViewById(R.id.ready_switch);
                    readySwitch.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            if (readySwitch.isChecked()){
                                mDatabaseReference.child("Cycle").child(uid).child("boolean").setValue("true");
                                //code for ready state
                                Toast.makeText(getActivity(),"Online and ready to lend", LENGTH_SHORT).show();
                            }
                            else{
                                mDatabaseReference.child("Cycle").child(uid).child("boolean").setValue("false");
                                //code for non ready state
                                Toast.makeText(getActivity(),"Offline", LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else{
                    TextView myBikeView = new TextView(getContext());
                    myBikeView.setText("Go to the menu and put your bike up for renting");
                    myBikeView.setGravity(1);
                    linearLayout.addView(myBikeView);
                }

                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mDatabaseReference.child("Cycle").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    bike = true;
                    bikeNameView = (TextView)myBikeView.findViewById(R.id.m_bike_name);
                    bikeAddressView = (TextView)myBikeView.findViewById(R.id.m_bike_add);
                    priceDayView = (TextView)myBikeView.findViewById(R.id.m_day_price);
                    priceHourView = (TextView)myBikeView.findViewById(R.id.m_hour_price);
                    priceWeekView = (TextView)myBikeView.findViewById(R.id.m_week_price);
                    bikeImage = (ImageView)myBikeView.findViewById(R.id.bike_Img);

                    mDatabaseReference.child("Cycle").child(uid).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Bike currentBike = new Bike();
                                currentBike.setBikename(dataSnapshot.getValue(Bike.class).getBikename());
                                bikeName = currentBike.getBikename();
                                currentBike.setBikeAddress(dataSnapshot.getValue(Bike.class).getBikeAddress());
                                bikeAddress = currentBike.getBikeAddress();
                                currentBike.setPrice(dataSnapshot.getValue(Bike.class).getPrice());
                                price = currentBike.getPrice();
                                bikeNameView.setText(bikeName);
                                bikeAddressView.setText(bikeAddress);
                                priceHourView.setText(String.valueOf(price.getHourly()));
                                priceDayView.setText(String.valueOf(price.getDaily()));
                                priceWeekView.setText(String.valueOf(price.getWeekly()));
                                StorageReference storageRef = mstorage.child("Cycle").child("/"+uid);
                                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Picasso.with(bikeImage.getContext()).load(uri).fit().into(bikeImage);
                                    }
                                });
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                    /*//Switch code for ready and non ready state switching
                    final Switch readySwitch = (Switch) myView.findViewById(R.id.ready_switch);
                    readySwitch.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            if (readySwitch.isChecked()){
                                mDatabaseReference.child("Cycle").child(uid).child("boolean").setValue("true");
                                //code for ready state
                                Toast.makeText(getActivity(),"Online and ready to lend", LENGTH_SHORT).show();
                            }
                            else{
                                mDatabaseReference.child("Cycle").child(uid).child("boolean").setValue("false");
                                //code for non ready state
                                Toast.makeText(getActivity(),"Offline", LENGTH_SHORT).show();
                            }
                        }
                    });*/

                }

                else {
                    bike = false;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return myView;
    }


}
