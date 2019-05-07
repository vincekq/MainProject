package com.towmec.mainproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class DriverSignUp2 extends AppCompatActivity implements View.OnClickListener {
    private FloatingActionButton log2;
    ProgressBar progressBar;
    EditText Manufacturer, Licenseplate, Trucktype;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_sign_up2);
        Manufacturer = findViewById(R.id.manufacturer);
        Licenseplate = (EditText) findViewById(R.id.licenseplate);
        Trucktype=(EditText) findViewById(R.id.trucktype);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        mAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user!=null){
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    Intent intent = new Intent(DriverSignUp2.this, Driverlogin.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };


        findViewById(R.id.btnextPage2).setOnClickListener((View.OnClickListener) this);
        log2   = (FloatingActionButton) findViewById(R.id.btnextPage2);


        log2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String manufacturer = Manufacturer.getText().toString().trim();
                final String licenseplate = Licenseplate.getText().toString().trim();
                final String trucktype = Trucktype.getText().toString().trim();

                Boolean isValid = true;
                if (manufacturer.isEmpty()) {
                    Manufacturer.setError("Enter your truck manufacturer here");
                    Manufacturer.requestFocus();
                    isValid = false;
                }
                if (licenseplate.isEmpty()) {
                    Licenseplate.setError("Number plate is required");
                    Licenseplate.requestFocus();
                    isValid = false;
                }
                if (trucktype.isEmpty()) {
                    Trucktype.setError("Truck type is required");
                    Trucktype.requestFocus();
                    isValid = false;
                }

                progressBar.setVisibility(View.VISIBLE);
                String user_id = mAuth.getCurrentUser().getUid();
                DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(user_id);
                current_user_db.child("Truck Information").child("Truck manufacturer").setValue(Manufacturer.getText().toString());
                current_user_db.child("Truck Information").child("License plate").setValue(Licenseplate.getText().toString());
                current_user_db.child("Truck Information").child("Truck type").setValue(Trucktype.getText().toString());
                Toast.makeText(getApplicationContext(), "Truck Information successfully registered", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(DriverSignUp2.this, DriverSignup3.class);
                startActivity(intent);
                finish();
            }

    });
}

    @Override
    public void onClick(View v) {
        log2.setOnClickListener(this);
    }
}
