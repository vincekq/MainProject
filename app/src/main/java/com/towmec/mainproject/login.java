package com.towmec.mainproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class login extends AppCompatActivity {
    ProgressBar progressBar;
    EditText editTextEmail;
    EditText editTextPassword;
    String codeSent;
    private TextView intro;
    private TextView brief;
    private Button mdriver;
    private Button mlearn;
    private FirebaseAuth mAuth;
    private PrefsManager prefsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prefsManager = new PrefsManager(this);

        intro = (TextView) findViewById(R.id.intro);
        brief = (TextView) findViewById(R.id.brief);
        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.mytransition);
        intro.startAnimation(myanim);
        brief.startAnimation(myanim);
        mAuth = FirebaseAuth.getInstance();
        mdriver = (Button) findViewById(R.id.but2);
        mlearn = (Button) findViewById(R.id.but1);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.Progressbar);


        findViewById(R.id.GetVerification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });


        mdriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentUser = mAuth.getCurrentUser();

                if (currentUser != null) {
                    Intent intent = new Intent(login.this, DriverMapsActivity.class);
                    Toast.makeText(getApplicationContext(), "Redirecting you to Driver Map..", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();

                } else {
                    Intent intent = new Intent(login.this, Driverlogin.class);
                    startActivity(intent);
                    finish();
                }
            }

        });

        mlearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, learntips.class);
                startActivity(intent);
                finish();
                return;
            }
        });
    }

    private boolean registerUser() {
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        Boolean isValid = true;

        if (email.isEmpty()) {
            editTextEmail.setError("Username is required");
            editTextEmail.requestFocus();
            isValid = false;
        }
        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            isValid = false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email address");
            editTextEmail.requestFocus();
            isValid = false;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            isValid = false;
        }
        if (password.length() < 6) {
            editTextPassword.setError("Minimum length should be 6 characters");
            editTextPassword.requestFocus();
            isValid = false;
        }
        if (!isValid) {
            return false;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    String user_id = mAuth.getCurrentUser().getUid();
                    String user_details = mAuth.getCurrentUser().getEmail();

                    DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(user_id);
                    current_user_db.child("PersonalInformation").child("email").setValue(editTextEmail.getText().toString());
                    Toast.makeText(getApplicationContext(), "You have been successfully registered", Toast.LENGTH_SHORT).show();

                    prefsManager.setUserType("customer");
                    prefsManager.setUserEmail(user_details);

                    Intent intent = new Intent(login.this, CustomerMapActivity.class);
                    prefsManager.setIsLoggedIn();
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(getApplicationContext(), "Some error occurred during signup", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return true;
    }
}


