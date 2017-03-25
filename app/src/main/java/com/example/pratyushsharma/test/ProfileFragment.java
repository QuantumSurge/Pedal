package com.example.pratyushsharma.test;


import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.media.ImageWriter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


public class ProfileFragment extends Fragment{
    private ImageView profile_pic;
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mFirebaseDatabase;
    private String uid;
    private StorageReference mstorage;
    private FirebaseAuth mFirebaseAuth;


    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_profile, container, false);

        profile_pic = (ImageView) myView.findViewById(R.id.profile_pic);

        mFirebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mFirebaseAuth.getCurrentUser();
        uid = user.getUid();

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

        //test user bike
        //Check whether the user has uploaded a bike or not
        LinearLayout linearLayout = (LinearLayout) myView.findViewById(R.id.myBikeCard);
        View myBikeView = inflater.inflate(R.layout.list_item2,container,false);
        linearLayout.addView(myBikeView);

        //Switch code for ready and non ready state switching
        final Switch readySwitch = (Switch) myView.findViewById(R.id.ready_switch);
        readySwitch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (readySwitch.isChecked()){
                    //code for ready state
                    Toast.makeText(getActivity(),"Locked and Loaded",Toast.LENGTH_SHORT).show();
                }
                else{
                    //code for non ready state
                    Toast.makeText(getActivity(),"Offline",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return myView;
    }

}
