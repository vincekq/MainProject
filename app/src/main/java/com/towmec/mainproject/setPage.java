package com.towmec.mainproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    private TextView editTextfirstname, editTextlastname, editTextphone;
    private FirebaseAuth mAuth;
    private Button Editprofile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_page);


        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        actionbar.setTitle("Settings");

        mAuth = FirebaseAuth.getInstance();
        DatabaseReference userdb = FirebaseDatabase.getInstance().getReference();
        editTextfirstname = findViewById(R.id.firstname);
        editTextlastname = findViewById(R.id.lastname);
        editTextphone = findViewById(R.id.phonenumber);

        Editprofile = findViewById(R.id.editprofilebtn);

        Editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editprofileintent = new Intent(setPage.this, editprofile.class);
                startActivity(editprofileintent);
                finish();
            }
    });
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mAuth = FirebaseAuth.getInstance();
                

                Userdetails userdetails = dataSnapshot.getValue(Userdetails.class);
                editTextfirstname.setText(userdetails.getFirstname());
                editTextlastname.setText(userdetails.getLastname());
                editTextphone.setText(userdetails.getPhonenumber());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        userdb.addValueEventListener(listener);


    }
}
