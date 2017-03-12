package com.example.pratyushsharma.test;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


public class Mainpage extends AppCompatActivity {

    private TextView welcome;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mFirebaseDatabase;
    private String name;
    private String uid;
    private StorageReference mstorage;
    private ImageView imageView;
    private String image;
    private Uri profile;


     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        welcome = (TextView) findViewById(R.id.textView);

        mFirebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mFirebaseAuth.getCurrentUser();
        uid = user.getUid();

        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();
         mstorage= FirebaseStorage.getInstance().getReference();




         imageView = (ImageView) findViewById(R.id.imageView);




        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                Userinfo userinfo = new Userinfo();

                userinfo.setUsername(dataSnapshot.child(uid).getValue(Userinfo.class).getUsername());

                welcome.setText("Welcome "+ userinfo.getUsername());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
         StorageReference storageRef = mstorage.child(uid).child("/profile.jpg");
         storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
             @Override
             public void onSuccess(Uri uri) {
                 Picasso.with(Mainpage.this).load(uri).fit().into(imageView);
             }
         });








    }
}
