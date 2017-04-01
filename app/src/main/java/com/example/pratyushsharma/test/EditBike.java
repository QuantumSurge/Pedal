package com.example.pratyushsharma.test;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.R.attr.name;
import static android.os.Build.VERSION_CODES.M;
import static com.example.pratyushsharma.test.R.id.dailyPicker;
import static com.example.pratyushsharma.test.R.id.hourlyPicker;
import static com.example.pratyushsharma.test.R.id.room;
import static com.example.pratyushsharma.test.R.id.upload;
import static com.example.pratyushsharma.test.R.id.weeklyPicker;
import static java.security.AccessController.getContext;

public class EditBike extends AppCompatActivity {

    private Button edit;
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
    private int PICK_IMAGE_REQUEST = 4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        progress = new ProgressDialog(this);
        setContentView(R.layout.activity_add_bike);
        edit = (Button) findViewById(R.id.upload);
        edit.setText("Edit");
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Bundle bundle = getIntent().getExtras();
        String[] bikeAdd = bundle.getStringArray("Bike Address");
        String bikeName = bundle.getString("Bike Name");
        String hourPrice = bundle.getString("Hour Price");
        String dayPrice = bundle.getString("Day Price");
        String weekPrice = bundle.getString("Week Price");

        Spinner hourSpinner = (Spinner) findViewById(R.id.address_spinner);
        NumberPicker hourPicker = (NumberPicker) findViewById(hourlyPicker);
        hourPicker.setMaxValue(getBaseContext().getResources().getInteger(R.integer.maxPriceForHour));
        hourPicker.setMinValue(getBaseContext().getResources().getInteger(R.integer.minPriceForHour));
        hourPicker.setWrapSelectorWheel(true);
        NumberPicker dayPicker = (NumberPicker) findViewById(dailyPicker);
        dayPicker.setMaxValue(getBaseContext().getResources().getInteger(R.integer.maxPriceForDay));
        dayPicker.setMinValue(getBaseContext().getResources().getInteger(R.integer.minPriceForDay));
        dayPicker.setWrapSelectorWheel(true);
        NumberPicker weekPicker = (NumberPicker) findViewById(weeklyPicker);
        weekPicker.setMaxValue(getBaseContext().getResources().getInteger(R.integer.maxPriceForWeek));
        weekPicker.setMinValue(getBaseContext().getResources().getInteger(R.integer.minPriceForWeek));
        weekPicker.setWrapSelectorWheel(true);

        EditText mBikeAdd = (EditText) findViewById(R.id.room);
        EditText mBikeName = (EditText) findViewById(R.id.bike_name);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.hostelNames,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hourSpinner.setAdapter(adapter);

        mBikeAdd.setText(bikeAdd[1]);
        mBikeName.setText(bikeName);
        hourSpinner.setSelection(adapter.getPosition(bikeAdd[0]));
        hourPicker.setValue(Integer.valueOf(hourPrice));
        dayPicker.setValue(Integer.valueOf(dayPrice));
        weekPicker.setValue(Integer.valueOf(weekPrice));

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

                            Bike bike =new Bike(mBikename,mBikeAddress,mUID,mBoolean,Price);
                            databasereference = FirebaseDatabase.getInstance().getReference();
                            databasereference.child("Cycle").child(user.getUid()).setValue(bike);
                            progress.dismiss();
                            Toast.makeText(EditBike.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(EditBike.this,ProfileFragment.class));
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
                progress.dismiss();

            }
        });

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout( (int) (width*.9) , (int) (height*.7) );


    }
}
