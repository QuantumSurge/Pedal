package com.example.pratyushsharma.test;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

/**
 * Created by Vikramaditya Patil on 17-03-2017.
 */

public class BikeDetail extends AppCompatActivity {
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private String number;
    private StorageReference mstorage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bike_detail);

        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();
        mstorage = FirebaseStorage.getInstance().getReference();

        Bundle bundle = getIntent().getExtras();
        final String uid = bundle.getString("uid");
        String bikeName = bundle.getString("bikeName");
        String bikeAddress = bundle.getString("bikeAddress");
        int priceHourly = bundle.getInt("priceHourly");
        int priceDaily = bundle.getInt("priceDaily");
        int priceWeekly = bundle.getInt("priceWeekly");

        final ImageView bikeimg_rentnow = (ImageView) findViewById(R.id.bikeimg_rentnow);


        StorageReference storageRef = mstorage.child("Cycle").child("/"+uid);
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(bikeimg_rentnow.getContext()).load(uri).fit().into(bikeimg_rentnow);
            }
        });
        FloatingActionButton call = (FloatingActionButton) findViewById(R.id.callbtn);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseReference.child(uid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Userinfo userinfo = new Userinfo();

                        userinfo.setMobile(dataSnapshot.getValue(Userinfo.class).getMobile());
                        number = userinfo.getMobile();

                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:"+ number));
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });

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
