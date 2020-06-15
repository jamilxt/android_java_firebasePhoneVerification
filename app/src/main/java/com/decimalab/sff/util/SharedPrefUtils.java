package com.decimalab.sff.util;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SharedPrefUtils {
    public static String getAuthToken(Context mContext) {
        SharedPreferences currPrefs = mContext.getSharedPreferences(Constants.LOGIN_SHARED_PREFERENCE, MODE_PRIVATE);
        return currPrefs.getString("token", "");
    }

    public static String getCurrentUserID(Context mContext) {
        SharedPreferences currPrefs = mContext.getSharedPreferences(Constants.LOGIN_SHARED_PREFERENCE, MODE_PRIVATE);
        return currPrefs.getString("phone", "");
    }

    public static String getIsUserExist(Context mContext) {
        SharedPreferences currPrefs = mContext.getSharedPreferences(Constants.LOGIN_SHARED_PREFERENCE, MODE_PRIVATE);
        return currPrefs.getString("exist", "");
    }
}
