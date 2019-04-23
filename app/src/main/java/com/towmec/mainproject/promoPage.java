package com.towmec.mainproject;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class promoPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo_page);


        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("Promotions");
    }
}
