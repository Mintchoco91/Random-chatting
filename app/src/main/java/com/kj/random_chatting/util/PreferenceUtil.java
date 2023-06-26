package com.kj.random_chatting.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtil {
    private static PreferenceUtil instance;
    private Context mContext;
    private static SharedPreferences prefs;
    private static SharedPreferences.Editor prefsEditor;


    public static synchronized PreferenceUtil init(Context context) {
        if (instance == null) {
            instance = new PreferenceUtil(context);
        }
        return instance;
    }

    private PreferenceUtil(Context context) {
        mContext = context;
        prefs = mContext.getSharedPreferences("token_prefs", Context.MODE_PRIVATE);
        prefsEditor = prefs.edit();
    }

    public static String getAccessToken(String defValue) {
        return prefs.getString("access_token", defValue);
    }

    public static String getRefreshToken(String defValue) {
        return prefs.getString("refresh_token", defValue);
    }

    public static String getUserId(String defValue) {
        return prefs.getString("userId", defValue);
    }



    public static void setAccessToken(String value) {
        prefsEditor.putString("access_token", value).commit();
    }

    public static void setRefreshToken(String value) {
        prefsEditor.putString("refresh_token", value).commit();
    }
}
