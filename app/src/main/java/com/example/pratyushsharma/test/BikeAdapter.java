package com.example.pratyushsharma.test;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class BikeAdapter<T> extends ArrayAdapter<Bike> {
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private StorageReference mstorage;

    public BikeAdapter(Activity context, ArrayList<Bike> pBikeList) {
        super(context, 0, pBikeList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();
        mstorage = FirebaseStorage.getInstance().getReference();

        // Get the {@link AndroidFlavor} object located at this position in the list
        Bike currentBike = getItem(position);
        String uid = currentBike.getUID();
        StorageReference storageRef = mstorage.child("Cycle").child("/"+uid);
        final ImageView bike_Img = (ImageView) listItemView.findViewById(R.id.bike_Img);
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(bike_Img.getContext()).load(uri).fit().into(bike_Img);
            }
        });


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






        return listItemView;
    }

}

