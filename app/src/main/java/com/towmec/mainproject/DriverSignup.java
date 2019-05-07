package com.towmec.mainproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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

import java.util.HashMap;
import java.util.Map;

public class DriverSignup extends AppCompatActivity implements View.OnClickListener {
    ProgressBar progressBar;
    EditText Username, Password, Email;
    EditText Phonenumber, Confirmpassword;
    private FloatingActionButton log1;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private PrefsManager prefsManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_signup);

        prefsManager = new PrefsManager(this);

        Username = findViewById(R.id.DUsername);
        Email = (EditText) findViewById(R.id.DEmail);
        Password = (EditText) findViewById(R.id.DPassword);
        Confirmpassword = findViewById(R.id.confirmpassword);
        Phonenumber = findViewById(R.id.DPhoneNumber);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        mAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    Intent intent = new Intent(DriverSignup.this, DriverMapsActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };

        findViewById(R.id.btnextPage).setOnClickListener(this);


        log1 = (FloatingActionButton) findViewById(R.id.btnextPage);

        log1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean userCreated = registerUser();
                if (userCreated) {
                    Intent intent = new Intent(DriverSignup.this, DriverSignUp2.class);
                    prefsManager.setIsLoggedIn();
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private boolean registerUser() {
        final String username = Username.getText().toString().trim();
        final String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();
        final String phonenumber = Phonenumber.getText().toString().trim();
        final String confirmpassword = Confirmpassword.getText().toString().trim();

        Boolean isValid = true;
        if (username.isEmpty()) {
            Username.setError("Username is required");
            Username.requestFocus();
            isValid = false;
        }
        if (email.isEmpty()) {
            Email.setError("Email is required");
            Email.requestFocus();
            isValid = false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Email.setError("Please enter a valid email address");
            Email.requestFocus();
            isValid = false;
        }
        if (phonenumber.length() != 10) {
            Phonenumber.setError("The number entered must contain 10 digits");
            Phonenumber.requestFocus();
            isValid = false;
        }
        if (password.isEmpty()) {
            Password.setError("Password is required");
            Password.requestFocus();
            isValid = false;
        }
        if (password.length() < 6) {
            Password.setError("Minimum length should be 6 characters");
            Password.requestFocus();
            isValid = false;
        }
        if (!confirmpassword.equals(password)) {
            Confirmpassword.setError("Passwords not matching");
            Confirmpassword.requestFocus();
            isValid = false;
        }
        if (!isValid) {
            return false;
        }

        progressBar.setVisibility(View.VISIBLE);


        /* SIGNING UP WITH EMAIL AND PASSWORD*/
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    String user_id = mAuth.getCurrentUser().getUid();
                    String user_details = mAuth.getCurrentUser().getEmail();
                    DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(user_id);
                    current_user_db.child("PersonalInformation").child("username").setValue(Username.getText().toString());
                    current_user_db.child("PersonalInformation").child("email").setValue(Email.getText().toString());
                    current_user_db.child("PersonalInformation").child("phonenumber").setValue(Phonenumber.getText().toString());
                    Toast.makeText(getApplicationContext(), "Driver Information successfully registered", Toast.LENGTH_SHORT).show();

                    prefsManager.setUserType("driver");
                    prefsManager.setUserEmail(user_details);
                } else {
                    Toast.makeText(getApplicationContext(), "Some error occurred during signup", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return true;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnextPage:
                registerUser();
                break;
        }

    }
}
