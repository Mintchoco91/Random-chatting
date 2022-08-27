package com.kj.random_chatting.userRegist;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.kj.random_chatting.R;
import com.kj.random_chatting.userMatching.UserMatchingFragment;

public class MainActivity extends FragmentActivity {
    private Fragment matchingFragment, chattingFragment, mapFragment, infoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * 하단 네비게이션 기본 설정 시작
         */

        matchingFragment = new UserMatchingFragment();
        chattingFragment = new UserMatchingFragment();
        mapFragment = new UserMatchingFragment();
        infoFragment = new UserMatchingFragment();

        BottomNavigationView navView = findViewById(R.id.nav_view);

        navView.setOnItemSelectedListener(
                new NavigationBarView.OnItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.navigation_matching:
                                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, matchingFragment).commit();
                                return true;
                            case R.id.navigation_chatting:
                                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, chattingFragment).commit();
                                return true;
                            case R.id.navigation_location:
                                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, mapFragment).commit();
                                return true;
                            case R.id.navigation_info:
                                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, infoFragment).commit();
                                return true;
                        }
                        return false;
                    }
                }
        );

        navView.setSelectedItemId(R.id.navigation_matching);

        /**
         * 하단 네비게이션 기본 설정 끝
         */


    }

}