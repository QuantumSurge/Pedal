package com.example.pratyushsharma.test;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;


public class AddBike extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 4;
    ArrayAdapter<CharSequence> adapter;
    private TextView name;
    private TextView room;
    private Spinner hostelSpinner;
    private NumberPicker hourly;
    private NumberPicker daily;
    private NumberPicker weekly;
    private DatabaseReference databasereference;
    private FirebaseAuth firebaseAuth;
    private ImageView bikeimg;
    private Uri filePath;
    private StorageReference uploadimg;
    private ProgressDialog progress;
    private Button upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bike);

        name = (TextView) findViewById(R.id.bike_name);
        room = (TextView) findViewById(R.id.room);
        hostelSpinner = (Spinner) findViewById(R.id.address_spinner);
        hourly = (NumberPicker) findViewById(R.id.hourlyPicker);
        daily = (NumberPicker) findViewById(R.id.dailyPicker);
        weekly = (NumberPicker) findViewById(R.id.weeklyPicker);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseAuth = FirebaseAuth.getInstance();
        progress = new ProgressDialog(this);
        upload = (Button) findViewById(R.id.upload);
        bikeimg = (ImageView) findViewById(R.id.bike_Img);
        uploadimg = FirebaseStorage.getInstance().getReference().child("Cycle");

        ImageView bikeImage = (ImageView) findViewById(R.id.bike_Img);
        bikeImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "Select an image"), PICK_IMAGE_REQUEST);
                Toast.makeText(getBaseContext(),"upload image",Toast.LENGTH_LONG);
            }
        });


        upload.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                if (filePath != null) {

                    progress.setMessage("Uploading...");
                    progress.show();

                    final StorageReference profileRef = uploadimg.child(user.getUid());


                    profileRef.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            String mBikename = name.getText().toString();
                            String mhostel = hostelSpinner.getSelectedItem().toString();
                            String mroom= room.getText().toString();
                            String mBikeAddress = mhostel + " " + mroom;
                            int mHourly = hourly.getValue();
                            int mDaily = daily.getValue();
                            int mWeekly= weekly.getValue();
                            String mUID = user.getUid();
                            String mBoolean = "true";
                            Price Price = new Price(mHourly,mDaily,mWeekly);

                            Bike bike =new Bike(mBikename,mBikeAddress,mUID,mBoolean,"false",Price);
                            databasereference = FirebaseDatabase.getInstance().getReference();
                            databasereference.child("Cycle").child(user.getUid()).setValue(bike);
                            progress.dismiss();
                            Toast.makeText(AddBike.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddBike.this,ProfileFragment.class));
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    progress.dismiss();
                                    Toast.makeText(AddBike.this, "Please try uploading the image again", Toast.LENGTH_SHORT).show();
                                    AddBike.this.recreate();
                                }
                            });
                }
                progress.dismiss();

            }
        });


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout( (int) (width*.9) , (int) (height*.7) );


        adapter = ArrayAdapter.createFromResource(this,R.array.hostelNames,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hostelSpinner.setAdapter(adapter);


        NumberPicker dailyPicker = (NumberPicker) findViewById(R.id.dailyPicker);
        dailyPicker.setMaxValue(getBaseContext().getResources().getInteger(R.integer.maxPriceForDay));
        dailyPicker.setMinValue(getBaseContext().getResources().getInteger(R.integer.minPriceForDay));
        dailyPicker.setWrapSelectorWheel(true);

        NumberPicker hourlyPicker = (NumberPicker) findViewById(R.id.hourlyPicker);
        hourlyPicker.setMaxValue(getBaseContext().getResources().getInteger(R.integer.maxPriceForHour));
        hourlyPicker.setMinValue(getBaseContext().getResources().getInteger(R.integer.minPriceForHour));
        hourlyPicker.setWrapSelectorWheel(true);

        NumberPicker weeklyPicker = (NumberPicker) findViewById(R.id.weeklyPicker);
        weeklyPicker.setMaxValue(getBaseContext().getResources().getInteger(R.integer.maxPriceForWeek));
        weeklyPicker.setMinValue(getBaseContext().getResources().getInteger(R.integer.minPriceForWeek));
        weeklyPicker.setWrapSelectorWheel(true);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                bikeimg.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
