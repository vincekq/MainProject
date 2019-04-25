package com.towmec.mainproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    private TextView intro;
    private TextView brief;
    private Button   mdriver;
    private Button   mlearn;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;
    EditText editTextPhone;
    EditText editTextUser;
    String codeSent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        intro = (TextView) findViewById(R.id.intro);
        brief = (TextView) findViewById(R.id.brief);
        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.mytransition);
        intro.startAnimation(myanim);
        brief.startAnimation(myanim);
        mAuth = FirebaseAuth.getInstance();
        mdriver = (Button) findViewById(R.id.but2);
        mlearn  = (Button) findViewById(R.id.but1);
        editTextPhone = findViewById(R.id.Pnumber);
        editTextUser = findViewById(R.id.Uname);
        progressBar = (ProgressBar) findViewById(R.id.Progressbar);


        findViewById(R.id.GetVerification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVerificationCode();
            }
        });


        mdriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentUser = mAuth.getCurrentUser();

                if(currentUser != null){
                    Intent intent = new Intent(login.this, DriverMapsActivity.class);
                    startActivity(intent);
                    finish();

                } else{
                    Intent intent = new Intent(login.this, Driverlogin.class);
                    startActivity(intent);
                    finish();
                }
            }

        });

        mlearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this,learntips.class);
                startActivity(intent);
                finish();
                return;
            }
        });


   }

    private void sendVerificationCode(){

        String phone = editTextPhone.getText().toString();
        String username = editTextUser.getText().toString();


        if(username.isEmpty()){
            editTextUser.setError("Username is required");
            editTextUser.requestFocus();
            return;
        }
        if(phone.isEmpty()){
            editTextPhone.setError("Phone number is required");
            editTextPhone.requestFocus();
            return;
        }

        if(phone.length() < 10 ){
            editTextPhone.setError("Please enter a valid phone");
            editTextPhone.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codeSent = s;
            /*String user_id = mAuth.getCurrentUser().getUid();
            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(user_id);
            current_user_db.child("PersonalInformation").child("phone number").setValue(editTextPhone.getText().toString());
            current_user_db.child("PersonalInformation").child("username").setValue(editTextUser.getText().toString());*/
            Intent intent = new Intent(login.this, login2.class);
            startActivity(intent);
            finish();
        }
    };


}
