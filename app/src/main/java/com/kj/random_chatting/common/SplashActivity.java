package com.kj.random_chatting.common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.kj.random_chatting.R;
import com.kj.random_chatting.login.LoginActivity;
import com.kj.random_chatting.userList.UserListActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                SharedPreferences prefs = getSharedPreferences("token_prefs", MODE_PRIVATE);
                String token = prefs.getString("token", null );
                if (token == null) {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, UserListActivity.class);
                }

                startActivity(intent);
                finish();
            }
        },2000);
    }
}