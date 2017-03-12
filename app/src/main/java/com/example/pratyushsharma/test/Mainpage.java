package com.example.pratyushsharma.test;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Mainpage extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mFirebaseDatabase;
    private String uid;


     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mainpage);


        mFirebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mFirebaseAuth.getCurrentUser();
        uid = user.getUid();

        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();


        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Userinfo userinfo = new Userinfo();

                userinfo.setUsername(dataSnapshot.child(uid).getValue(Userinfo.class).getUsername());

                setTitle("Welcome " + userinfo.getUsername());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
         CategoryAdapter adapter = new CategoryAdapter(this, getSupportFragmentManager());
         viewPager.setAdapter(adapter);

         TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
         tabLayout.setupWithViewPager(viewPager);
     }



}
