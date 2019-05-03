package com.towmec.mainproject;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class editprofile extends AppCompatActivity {
    private static final int SELECT_PICTURE = 0;
    private static int RESULT_LOAD_IMAGE = 1;
    ImageView imageView;
    EditText Firstname, Lastname, Email, Phonenumber;
    Button save;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);


        ActionBar actionbar = getSupportActionBar();
        if(actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            actionbar.setTitle("Edit Profile");
        }

        Firstname = findViewById(R.id.firstname);
        Lastname = findViewById(R.id.lastname);
        Email = findViewById(R.id.email);
        Phonenumber = findViewById(R.id.phonenumber);

        imageView = (ImageView) findViewById(R.id.upload);
        save = findViewById(R.id.Save);
        mAuth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth = FirebaseAuth.getInstance();
                final String userId = mAuth.getCurrentUser().getUid();
                final DatabaseReference userdb = FirebaseDatabase.getInstance().getReference();

                Userdetails userprofiledetails = new Userdetails();
                userprofiledetails.setFirstname(Firstname.getText().toString());
                userprofiledetails.setLastname(Lastname.getText().toString());
                userprofiledetails.setEmail(Email.getText().toString());
                userprofiledetails.setPhonenumber(Phonenumber.getText().toString());

                userdb.child("Users").child("Customers").child(userId).child("PersonalInformation").setValue(userprofiledetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "User Profile update successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(editprofile.this, setPage.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Log.d("test", task.toString());
                            Toast.makeText(getApplicationContext(), "Unable to update profile", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }




        });



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

       if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = (ImageView) findViewById(R.id.profile_picture);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }

    }
}

