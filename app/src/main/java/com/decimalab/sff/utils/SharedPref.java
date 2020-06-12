package com.decimalab.sff.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public SharedPref(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("setting", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveNumber(String number) {
        editor.putString("phone_number", number);
        editor.apply();
    }

    public String getSavedNumber(String number) {
        return sharedPreferences.getString("phone_number", null);
    }

}
