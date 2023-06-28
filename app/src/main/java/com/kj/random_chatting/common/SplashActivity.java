package com.kj.random_chatting.common;

import static com.kj.random_chatting.common.Constants.SHARED_PREFERENCES_NAME;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.kj.random_chatting.R;
import com.kj.random_chatting.login.LoginActivity;
import com.kj.random_chatting.onboarding.OnboardingActivity;
import com.kj.random_chatting.util.PreferenceUtil;
import com.kj.random_chatting.util.UtilClass;

public class SplashActivity extends Activity {
    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        PreferenceUtil.init(getApplicationContext());
        setContentView(R.layout.activity_splash_ver2);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
                String token = PreferenceUtil.getAccessToken(null);

                if (token == null) {
                    intent = new Intent(SplashActivity.this, OnboardingActivity.class);
                } else {
                    //정보 refresh query -> prefs 로 저장. LoginActivity 참조
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                }

                startActivity(intent);
                finish();
            }
        },2000);
    }
}