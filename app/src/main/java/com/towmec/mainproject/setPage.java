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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class setPage extends AppCompatActivity {
    private TextView editTextfirstname, editTextlastname, editTextphone, editTextemail;
    private FirebaseAuth mAuth;
    private Button Editprofile;

    private final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_page);


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
        DatabaseReference userDetailsRef = userdb.child("Users").child("Customers").child(userId).child("PersonalInformation");
        editTextfirstname = findViewById(R.id.editTextfirstname);
        editTextlastname = findViewById(R.id.editTextlastname);
        editTextphone = findViewById(R.id.editTextPhonenumber);
        editTextemail = findViewById(R.id.editTextEmailAddress);

        Editprofile = findViewById(R.id.editprofilebtn);

        Editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editprofileintent = new Intent(setPage.this, editprofile.class);
                startActivity(editprofileintent);
                finish();
            }
    });
    userDetailsRef.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            Userdetails userdetails = dataSnapshot.getValue(Userdetails.class);
            if(userdetails != null) {
                Log.d(TAG, "userdetails: "+userdetails.toString());
                editTextfirstname.setText(userdetails.getFirstname());
                editTextlastname.setText(userdetails.getLastname());
                editTextemail.setText(userdetails.getEmail());
                editTextphone.setText(userdetails.getPhonenumber());
            }else {
                Log.d(TAG, "userdetails is: "+userdetails);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });


    }
}
