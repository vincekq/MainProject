package com.towmec.mainproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DriverSignup4 extends AppCompatActivity {
    private Button log4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_signup4);

        log4   = (Button) findViewById(R.id.finto);

        log4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverSignup4.this, DriverMapsActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });
    }
}
