package com.example.pratyushsharma.test;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
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
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.IOException;

public class EditProfile extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;
    private StorageReference mstorage;
    private ImageView profilePic;
    private int PICK_IMAGE_REQUEST = 6;
    private Uri filePath;
    private Button edit;
    private ProgressDialog progress;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private EditText mobile;
    private TextView username;
    private String number;
    private String U_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout( (int) (width*.9) , (int) (height*.7) );

        mFirebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mFirebaseAuth.getCurrentUser();
        String uid = user.getUid();
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();
        mstorage= FirebaseStorage.getInstance().getReference();

        profilePic = (ImageView) findViewById(R.id.profile_pic_edit);
        edit = (Button) findViewById(R.id.edit_button);
        progress = new ProgressDialog(this);
        username = (TextView)findViewById(R.id.name_edit);
        mobile = (EditText) findViewById(R.id.mobile_edit);
        filePath = null;


        StorageReference storageRef = mstorage.child(uid).child("/profile.jpg");
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(profilePic.getContext()).load(uri).fit().into(profilePic);
            }
        });

        mDatabaseReference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Userinfo userinfo = new Userinfo();
                String mob_num;
                String name;

                userinfo.setMobile(dataSnapshot.getValue(Userinfo.class).getMobile());
                mob_num = userinfo.getMobile();
                mobile.setText(mob_num);

                userinfo.setUsername(dataSnapshot.getValue(Userinfo.class).getUsername());
                name = userinfo.getUsername();
                username.setText(name);
                U_name = name;

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "Select an image"), PICK_IMAGE_REQUEST);

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress.setMessage("Updating...");
                progress.show();
                if (filePath != null) {
                    final StorageReference profileRef = mstorage.child(user.getUid() + "/profile.jpg");


                    profileRef.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    progress.dismiss();
                                    Toast.makeText(EditProfile.this, "Please try again", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                number= mobile.getText().toString();
                Userinfo userinfo = new Userinfo(number,U_name);
                mDatabaseReference.child(user.getUid()).setValue(userinfo);

                startActivity(new Intent(EditProfile.this,Mainpage.class));
                Toast.makeText(EditProfile.this, "Profile pic will be updated upon restart.", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                profilePic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
