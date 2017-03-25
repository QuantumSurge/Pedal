package com.example.pratyushsharma.test;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private TextView loginemail;
    private TextView loginpassword;
    private Button login;
    private FirebaseAuth firebaseauth;
    private ProgressDialog progress;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginemail = (TextView) findViewById(R.id.email_login);
        loginpassword = (TextView) findViewById(R.id.password_login);

        login = (Button) findViewById(R.id.login_btn);
        register = (Button) findViewById(R.id.register_btn);

        progress = new ProgressDialog(this);

        firebaseauth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseauth.getCurrentUser();

        if(user == null){
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    login_to_profile();
                }
            });

            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Login.this,Signup.class));
                }
            });
        }
        else{
            SharedPreferences sharedpref = getSharedPreferences("userlogin", Context.MODE_PRIVATE);

            final String email = sharedpref.getString("email","");
            final String password = sharedpref.getString("password","");

            loginemail.setText(email);
            loginpassword.setText(password);

            progress.setMessage("Logging in...");
            progress.show();


            firebaseauth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                progress.dismiss();

                                finish();
                                Intent i = new Intent(Login.this, Mainpage.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);

                            }
                            else{
                                progress.dismiss();
                                Toast.makeText(Login.this,"Please Try Again",Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

        }

    }


    private void login_to_profile(){

        String email = loginemail.getText().toString().trim();
        String password = loginpassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter a valid email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter a valid password",Toast.LENGTH_SHORT).show();
            return;
        }

        progress.setMessage("Logging in...");
        progress.show();


        firebaseauth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progress.dismiss();

                    checkIfEmailVerified();

                }
                else{
                    progress.dismiss();
                    Toast.makeText(Login.this,"Please Try Again",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void checkIfEmailVerified() {		
         FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();		
 		
         if (user.isEmailVerified())		
         {		
             Toast.makeText(Login.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
 		
             finish();		
             Intent i = new Intent(Login.this, Mainpage.class);		
             i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);		
             startActivity(i);

             saveInfo();
 		
         }		
         else		
         {		
             FirebaseAuth.getInstance().signOut();		
             Toast.makeText(Login.this, "Please verify your e-mail.", Toast.LENGTH_SHORT).show();		
 		
         }		
     }

    private void saveInfo(){
         SharedPreferences sharedpref = getSharedPreferences("userlogin", Context.MODE_PRIVATE);
         SharedPreferences.Editor editor = sharedpref.edit();

         editor.putString("email",loginemail.getText().toString().trim());
         editor.putString("password",loginpassword.getText().toString().trim());
         editor.apply();
    }

}