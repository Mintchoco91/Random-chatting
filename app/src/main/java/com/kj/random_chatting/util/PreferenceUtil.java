package com.kj.random_chatting.util;

import static com.kj.random_chatting.common.Constants.SHARED_PREFERENCES_NAME;

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
        prefs = mContext.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
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

    public static String getCountryCode(String defValue) {
        return prefs.getString("countryCode", defValue);
    }

    public static String getPhoneNumber(String defValue) {
        return prefs.getString("phoneNumber", defValue);
    }

    public static String getNickName(String defValue) {
        return prefs.getString("nickName", defValue);
    }

    public static String getBirthday(String defValue) {
        return prefs.getString("birthday", defValue);
    }

    public static String getGender(String defValue) {
        return prefs.getString("gender", defValue);
    }

    public static String getEmail(String defValue) {
        return prefs.getString("email", defValue);
    }

    public static String getPassword(String defValue) {
        return prefs.getString("password", defValue);
    }

    public static String getFcmToken(String defValue) {
        return prefs.getString("fcmToken", defValue);
    }

    public static void setAccessToken(String value) {
        prefsEditor.putString("access_token", value).commit();
    }

    public static void setRefreshToken(String value) {
        prefsEditor.putString("refresh_token", value).commit();
    }

    public static void setUserId(String value) {
        prefsEditor.putString("userId", value).commit();
    }

    public static void setCountryCode(String value) {
        prefsEditor.putString("countryCode", value).commit();
    }

    public static void setPhoneNumber(String value) {
        prefsEditor.putString("phoneNumber", value).commit();
    }

    public static void setNickName(String value) {
        prefsEditor.putString("nickName", value).commit();
    }

    public static void setBirthday(String value) {
        prefsEditor.putString("birthday", value).commit();
    }

    public static void setGender(String value) {
        prefsEditor.putString("gender", value).commit();
    }

    public static void setEmail(String value) {
        prefsEditor.putString("email", value).commit();
    }

    public static void setPassword(String value) {
        prefsEditor.putString("password", value).commit();
    }

    public static void setFcmToken(String value) {
        prefsEditor.putString("fcmToken", value).commit();
    }
}
