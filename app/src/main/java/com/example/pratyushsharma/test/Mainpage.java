package com.example.pratyushsharma.test;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.widget.Toast.makeText;
import static java.security.AccessController.getContext;


public class Mainpage extends AppCompatActivity {

    public String userName;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mFirebaseDatabase;
    private String uid;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_mainpage, menu);
        Log.v("Mainpage","OnCreateOptions");
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_settings:
                FirebaseAuth.getInstance().signOut();
                mDatabaseReference.child("Cycle").child(uid).child("boolean").setValue("false");
                SharedPreferences preferences = getSharedPreferences("userlogin", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                Intent i = new Intent(Mainpage.this, Login.class);
                startActivity(i);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Toast.makeText(getBaseContext(),"Successfully Logged out",Toast.LENGTH_LONG).show();
                finish();
                return true;
            case R.id.lend_bike:

                if(mDatabaseReference.child("Cycle").child(uid) == null){
                    Intent addBikeIntent = new Intent(getBaseContext(),AddBike.class);
                    startActivity(addBikeIntent);
                return true;
                }
                else{
                    Toast.makeText(getBaseContext(),"You can only lend one bicycle.",Toast.LENGTH_LONG).show();
                    return true;
                }
            case R.id.edit_bike:
                Intent addBikeIntent = new Intent(getBaseContext(),AddBike.class);
                /*Bundle bundle = new Bundle();
                TextView addView = (TextView) findViewById(R.id.m_bike_add);
                String bikeAdd = addView.getText().toString();
                bundle.putString("Bike Address",bikeAdd);
                TextView nameView = (TextView) findViewById(R.id.m_bike_name);
                String bikeName = nameView.getText().toString();
                bundle.putString("Bike Name",bikeName);

                TextView hourView = (TextView) findViewById(R.id.hour_price);
                String hourPrice = hourView.getText().toString();
                bundle.putString("Hour Price",hourPrice);
                TextView dayView = (TextView) findViewById(R.id.day_price);
                String dayPrice = dayView.getText().toString();
                bundle.putString("Day Price",dayPrice);
                TextView weekView = (TextView) findViewById(R.id.week_price);
                String weekPrice = weekView.getText().toString();
                bundle.putString("Week Price",weekPrice);
                addBikeIntent.putExtras(bundle);*/
                startActivity(addBikeIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("mainpage","oncreatebeginning");
        setContentView(R.layout.activity_mainpage);
        Log.v("MainPage","setcontentview");
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        uid = user.getUid();
        Log.v("mainpage","firebaseasuth");
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();




        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Userinfo userinfo = new Userinfo();
                userinfo.setUsername(dataSnapshot.child(uid).getValue(Userinfo.class).getUsername());
                userName = userinfo.getUsername();
                setTitle("Welcome " + userName);
                TextView textUserName = (TextView) findViewById(R.id.user_name);
                textUserName.setText(userName);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        CategoryAdapter adapter = new CategoryAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

    }
}

