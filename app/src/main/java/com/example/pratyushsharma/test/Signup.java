package com.example.pratyushsharma.test;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import static com.example.pratyushsharma.test.R.id.confirmpassword_signup;

public class Signup extends AppCompatActivity {

    private TextView name_signup;
    private TextView room_signup;
    private TextView comfirmpassword_signup;
    private TextView email_signup;
    private TextView password_signup;
    private Button buttonsignup;
    private ProgressDialog progress;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databasereference;
    private ImageView chooseimage;
    private  static final int PICK_IMAGE_REQUEST =2;
    private Uri filePath;
    private StorageReference uploadimg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        comfirmpassword_signup = (TextView) findViewById(confirmpassword_signup);
        name_signup = (TextView)  findViewById(R.id.name_signup);
        email_signup = (TextView) findViewById(R.id.email_signup);
        password_signup = (TextView) findViewById(R.id.password_signup);
        room_signup = (TextView) findViewById(R.id.room_signup);

        buttonsignup = (Button) findViewById(R.id.signup_btn);

        progress = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();

        chooseimage = (ImageView) findViewById(R.id.choose);

        uploadimg = FirebaseStorage.getInstance().getReference();



        chooseimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i,"Select an image"),PICK_IMAGE_REQUEST);
            }
        });


        buttonsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            filePath = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                chooseimage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void registerUser(){

        final String email = email_signup.getText().toString().trim();

        final String password = password_signup.getText().toString().trim();

        final String room = room_signup.getText().toString().trim();

        final String name = name_signup.getText().toString().trim();

        String confirmpassword = comfirmpassword_signup.getText().toString().trim();

        databasereference = FirebaseDatabase.getInstance().getReference();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter a valid email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter a valid password",Toast.LENGTH_SHORT).show();
            return;
        }

        int length = password.length();

        if(length < 6 && length>0){
            Toast.makeText(this,"This password is too short",Toast.LENGTH_SHORT).show();
            return;
        }

        if(!password.equals(confirmpassword)){
            Toast.makeText(this,"Passwords don't match. Try again.",Toast.LENGTH_SHORT).show();
            return;
        }


        progress.setMessage("You are registering...");
        progress.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progress.dismiss();
                        if(task.isSuccessful()){

                            firebaseAuth.signInWithEmailAndPassword(email,password);

                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            if(filePath!=null) {
                                final StorageReference profileRef = uploadimg.child(user.getUid() + "/profile.jpg");

                                profileRef.putFile(filePath)
                                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception exception) {
                                                progress.dismiss();
                                                Toast.makeText(Signup.this, "Please Upload the image again", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }

                            sendVerificationEmail();

                            Userinfo userinfo = new Userinfo(room,name);
                            databasereference.child(user.getUid()).setValue(userinfo);

                        }
                        else {
                            Toast.makeText(Signup.this,"Please Try Again",Toast.LENGTH_SHORT).show();
                        }

                    }
                });




    }

    private void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Signup.this,"Registered Successfully. Please verify your e-mail.",Toast.LENGTH_SHORT).show();
                            // after email is sent just logout the user and finish this activity
                            FirebaseAuth.getInstance().signOut();

                            finish();
                            Intent i = new Intent(Signup.this, Login.class);
                            startActivity(i);
                        }
                        else
                        {
                            Toast.makeText(Signup.this,"There was an error. Please try again.",Toast.LENGTH_SHORT).show();

                            Signup.this.recreate();
                        }
                    }
                });
    }

}

