package com.example.pratyushsharma.test;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        Intent addBikeIntent = new Intent(getContext() , AddBike.class);
        startActivity(addBikeIntent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_profile, container, false);
        Button addBike = (Button) myView.findViewById(R.id.add_bike);
        addBike.setOnClickListener(this);
        return myView;

    }

}
