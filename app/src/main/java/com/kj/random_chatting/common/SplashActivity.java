package com.kj.random_chatting.common;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.kj.random_chatting.R;
import com.kj.random_chatting.login.LoginActivity;
import com.kj.random_chatting.onboarding.OnboardingActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_ver2);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                SharedPreferences prefs = getSharedPreferences("token_prefs", MODE_PRIVATE);
                String token = prefs.getString("token", null );
                if (token == null) {
                    intent = new Intent(SplashActivity.this, OnboardingActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                }

                startActivity(intent);
                finish();
            }
        },2000);
    }
}