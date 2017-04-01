package com.example.pratyushsharma.test;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


import static android.R.attr.tag;
import static com.example.pratyushsharma.test.R.id.dailyPicker;
import static com.example.pratyushsharma.test.R.id.hourlyPicker;
import static com.example.pratyushsharma.test.R.id.start;
import static com.example.pratyushsharma.test.R.id.weeklyPicker;

public class EditBike extends AppCompatActivity {

    private Button edit;
    private DatabaseReference databasereference;
    private Uri filePath;
    private StorageReference uploadimg;
    private int PICK_IMAGE_REQUEST = 4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        final ProgressDialog progress = new ProgressDialog(EditBike.this);
        setContentView(R.layout.activity_add_bike);
        edit = (Button) findViewById(R.id.upload);
        edit.setText("Edit");
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uploadimg = FirebaseStorage.getInstance().getReference().child("Cycle");

        Bundle bundle = getIntent().getExtras();
        final String[] bikeAdd = bundle.getStringArray("Bike Address");
        String bikeName = bundle.getString("Bike Name");
        String hourPrice = bundle.getString("Hour Price");
        String dayPrice = bundle.getString("Day Price");
        String weekPrice = bundle.getString("Week Price");
        Bitmap img = getIntent().getParcelableExtra("BitmapImage");

        final Spinner hostelSpinner = (Spinner) findViewById(R.id.address_spinner);
        final NumberPicker hourPicker = (NumberPicker) findViewById(hourlyPicker);
        hourPicker.setMaxValue(getBaseContext().getResources().getInteger(R.integer.maxPriceForHour));
        hourPicker.setMinValue(getBaseContext().getResources().getInteger(R.integer.minPriceForHour));
        hourPicker.setWrapSelectorWheel(true);
        final NumberPicker dayPicker = (NumberPicker) findViewById(dailyPicker);
        dayPicker.setMaxValue(getBaseContext().getResources().getInteger(R.integer.maxPriceForDay));
        dayPicker.setMinValue(getBaseContext().getResources().getInteger(R.integer.minPriceForDay));
        dayPicker.setWrapSelectorWheel(true);
        final NumberPicker weekPicker = (NumberPicker) findViewById(weeklyPicker);
        weekPicker.setMaxValue(getBaseContext().getResources().getInteger(R.integer.maxPriceForWeek));
        weekPicker.setMinValue(getBaseContext().getResources().getInteger(R.integer.minPriceForWeek));
        weekPicker.setWrapSelectorWheel(true);

        final EditText mBikeAdd = (EditText) findViewById(R.id.room);
        final EditText mBikeName = (EditText) findViewById(R.id.bike_name);
        final ImageView mBikeImg = (ImageView) findViewById(R.id.bike_Img);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.hostelNames,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hostelSpinner.setAdapter(adapter);

        mBikeAdd.setText(bikeAdd[1]);
        mBikeName.setText(bikeName);
        hostelSpinner.setSelection(adapter.getPosition(bikeAdd[0]));
        hourPicker.setValue(Integer.valueOf(hourPrice));
        dayPicker.setValue(Integer.valueOf(dayPrice));
        weekPicker.setValue(Integer.valueOf(weekPrice));
        mBikeImg.setImageBitmap(img);



        mBikeImg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "Select an image"), PICK_IMAGE_REQUEST);
                Toast.makeText(getBaseContext(),"upload image",Toast.LENGTH_LONG);
            }
        });



        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (filePath != null) {
                    progress.setMessage("Uploading...");
                    progress.show();

                    final StorageReference profileRef = uploadimg.child(user.getUid());


                    profileRef.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            String mBikename = mBikeName.getText().toString();
                            String mhostel = hostelSpinner.getSelectedItem().toString();
                            String mroom= bikeAdd[1];
                            String mBikeAddress = mhostel + " " + mroom;
                            int mHourly = hourPicker.getValue();
                            int mDaily = dayPicker.getValue();
                            int mWeekly= weekPicker.getValue();
                            String mUID = user.getUid();
                            String mBoolean = "true";
                            Price Price = new Price(mHourly,mDaily,mWeekly);

                            Bike bike =new Bike(mBikename,mBikeAddress,mUID,mBoolean,Price);
                            databasereference = FirebaseDatabase.getInstance().getReference();
                            databasereference.child("Cycle").child(user.getUid()).setValue(bike);
                            progress.dismiss();
                            Toast.makeText(EditBike.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(EditBike.this, ProfileFragment.class));
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    progress.dismiss();
                                    Toast.makeText(EditBike.this, "Please try uploading the image again", Toast.LENGTH_SHORT).show();
                                    EditBike.this.recreate();
                                }
                            });
                }
                if(filePath == null){
                    String mBikename = mBikeName.getText().toString();
                    String mhostel = hostelSpinner.getSelectedItem().toString();
                    String mroom= bikeAdd[1];
                    String mBikeAddress = mhostel + " " + mroom;
                    int mHourly = hourPicker.getValue();
                    int mDaily = dayPicker.getValue();
                    int mWeekly= weekPicker.getValue();
                    String mUID = user.getUid();
                    String mBoolean = "true";
                    Price Price = new Price(mHourly,mDaily,mWeekly);

                    Bike bike =new Bike(mBikename,mBikeAddress,mUID,mBoolean,Price);
                    databasereference = FirebaseDatabase.getInstance().getReference();
                    databasereference.child("Cycle").child(user.getUid()).setValue(bike);
                    progress.dismiss();
                    Toast.makeText(EditBike.this, "Uploaded", Toast.LENGTH_SHORT).show();
                    EditBike.this.finish();
                }
            }
        });


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout( (int) (width*.9) , (int) (height*.7) );

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                ImageView mBikeImg = (ImageView) findViewById(R.id.bike_Img);
                mBikeImg.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
