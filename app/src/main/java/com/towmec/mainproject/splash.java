package com.towmec.mainproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class splash extends AppCompatActivity {
    private TextView tv;
    private PrefsManager prefsManager;

    //private ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        prefsManager = new PrefsManager(this);

        tv = (TextView) findViewById(R.id.tv);
        // iv = (ImageView) findViewById(R.id.iv);
        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.mytransition);
        tv.startAnimation(myanim);
        // iv.startAnimation(myanim);
        final Intent i = new Intent(this, login.class);

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    boolean isLoggedIn = prefsManager.getIsLoggedIn();
                    if (!isLoggedIn) {
                        startActivity(i);
                        finish();
                    } else {
                        String userType = prefsManager.getUserType();
                        Log.d(splash.class.getSimpleName(), "User Type: " + userType);
                        if (userType.equalsIgnoreCase("customer")) {
                            Intent intent = new Intent(splash.this, CustomerMapActivity.class);
                            startActivity(intent);
                            finish();
                        } else if (userType.equalsIgnoreCase("driver")) {
                            Intent intent = new Intent(splash.this, DriverMapsActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            }
        };
        timer.start();

    }
}

