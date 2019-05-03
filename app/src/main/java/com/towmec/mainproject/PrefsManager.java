package com.towmec.mainproject;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefsManager {

    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private final int PRIVATE_MODE = 0;

    private final String USER_TYPE = "user_type";
    private final String IS_LOGGED_IN = "is_logged_in";
    private final String USER_EMAIL = "user_email";


    public PrefsManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences("CloudTow", PRIVATE_MODE);
        editor = preferences.edit();
    }

    public void setUserType(String userType) {
        editor.putString(USER_TYPE, userType).apply();
    }

    public String getUserType() {
        return preferences.getString(USER_TYPE, "customer");
    }

    public void setIsLoggedIn() {
        editor.putBoolean(IS_LOGGED_IN, true).apply();
    }

    public boolean getIsLoggedIn() {
        return preferences.getBoolean(IS_LOGGED_IN, false);
    }

    public void setUserEmail(String email) {
        editor.putString(USER_EMAIL, email).apply();
    }

    public String getUserEmail() {
        return preferences.getString(USER_EMAIL, null);
    }
}