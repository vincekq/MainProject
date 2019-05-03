package com.towmec.mainproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DriverSignup3 extends AppCompatActivity implements View.OnClickListener {
    private FloatingActionButton log3;
    ProgressBar progressBar;
    EditText Driverslicense, Nationalid, Insurance;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_signup3);
        Driverslicense = findViewById(R.id.driverslicense);
        Nationalid = (EditText) findViewById(R.id.nationalid);
        Insurance=(EditText) findViewById(R.id.insurance);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.btnextPage3).setOnClickListener((View.OnClickListener) this);
        log3   = (FloatingActionButton) findViewById(R.id.btnextPage3);


        log3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String driverslicense = Driverslicense.getText().toString().trim();
                final String nationalid = Nationalid.getText().toString().trim();
                final String insurance = Insurance.getText().toString().trim();

                Boolean isValid = true;
                if (driverslicense.isEmpty()) {
                    Driverslicense.setError("Enter a valid drivers license identification number");
                    Driverslicense.requestFocus();
                    isValid = false;
                }
                if (nationalid.isEmpty()) {
                    Nationalid.setError("NationalID is required");
                    Nationalid.requestFocus();
                    isValid = false;
                }
                if (insurance.isEmpty()) {
                    Insurance.setError("Insurance is required");
                    Insurance.requestFocus();
                    isValid = false;
                }
                progressBar.setVisibility(View.VISIBLE);
                String user_id = mAuth.getCurrentUser().getUid();
                DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(user_id);
                current_user_db.child("ID Information").child("drivers license").setValue(Driverslicense.getText().toString());
                current_user_db.child("ID Information").child("national id").setValue(Nationalid.getText().toString());
                current_user_db.child("ID Information").child("insurance").setValue(Insurance.getText().toString());

                Toast.makeText(getApplicationContext(), "Truck Information successfully registered", Toast.LENGTH_SHORT).show();

                FirebaseUser currentUser = mAuth.getCurrentUser();
                if(currentUser != null){
                    Intent intent = new Intent(DriverSignup3.this, DriverMapsActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Log.e(DriverMapsActivity.class.getSimpleName(), "Kindly log in");
                    Toast.makeText(getApplicationContext(), "Register details again", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    @Override
    public void onClick(View v) {
        log3.setOnClickListener(this);
    }
}
