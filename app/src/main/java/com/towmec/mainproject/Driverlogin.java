package com.towmec.mainproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Driverlogin extends AppCompatActivity {

    private Button Dsign;
    private Button Dlog;
    EditText Email, Password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driverlogin);
        Email = findViewById(R.id.DEmail);
        Password = findViewById(R.id.DPassword);
        mAuth = FirebaseAuth.getInstance();
        final String email = Email.getText().toString();
        final String password = Password.getText().toString();
        Dsign  = (Button) findViewById(R.id.Singup);
        Dlog   = (Button) findViewById(R.id.Dlogin);


        Dsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentUser = mAuth.getCurrentUser();

                if(currentUser != null){
                    Intent intent = new Intent(Driverlogin.this, DriverMapsActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Intent intent = new Intent(Driverlogin.this, DriverSignup.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }});


        Dlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = Email.getText().toString();
                final String password = Password.getText().toString();
                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(Driverlogin.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Email.setError("Please enter a valid email address");
                                Email.requestFocus();
                                Toast.makeText(Driverlogin.this, "sign in error", Toast.LENGTH_SHORT).show();
                            } else{
//
                                Intent intent = new Intent(Driverlogin.this,DriverMapsActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }}
        });
    }}
