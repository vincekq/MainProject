package com.towmec.mainproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class setdriver extends AppCompatActivity {
    private EditText editTextfullname, editTextPhone, editTextLicenseplate, editTextTruck;
    private FirebaseAuth mAuth;
    private Button Editprofile;
    private final String TAG = this.getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setdriver);

        ActionBar actionbar = getSupportActionBar();
        if(actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            actionbar.setTitle("Settings");
        }

        mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
//        Log.d(TAG, "currentUserId: "+mAuth.getUid());
        DatabaseReference userdb = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userDetailsRef = userdb.child("Users").child("Drivers").child(userId).child("PersonalInformation");
        DatabaseReference truckDetailsRef = userdb.child("Users").child("Drivers").child(userId).child("Truck Information");
        editTextfullname = findViewById(R.id.editTextfullname);
        editTextLicenseplate = findViewById(R.id.editTextLicenseplate);
        editTextPhone = findViewById(R.id.editTextPhonenumber);
        editTextTruck = findViewById(R.id.editTextTruck);

        Editprofile = findViewById(R.id.editprofilebtn);

        Editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editprofileintent = new Intent(setdriver.this, editdriverpage.class);
                startActivity(editprofileintent);
                finish();
            }
        });

        userDetailsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               Driverdetails driverdetails = dataSnapshot.getValue(Driverdetails.class);
                if(driverdetails != null) {
                    Log.d(TAG, "userdetails: "+driverdetails.toString());
                    editTextfullname.setText(driverdetails.getFullname());
                    editTextPhone.setText(driverdetails.getDPhonenumber());
                }else {
                    Log.d(TAG, "userdetails is: "+driverdetails);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        truckDetailsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Driverdetails driverdetails = dataSnapshot.getValue(Userdetails.class);
                if(driverdetails != null) {
                    Log.d(TAG, "userdetails: "+driverdetails.toString());
                    editTextLicenseplate.setText(driverdetails.getLicenseplate());
                    editTextTruck.setText(driverdetails.getTruck());
                }else {
                    Log.d(TAG, "userdetails is:"+driverdetails);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
