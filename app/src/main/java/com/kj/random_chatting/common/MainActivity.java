package com.kj.random_chatting.common;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.kj.random_chatting.R;
import com.kj.random_chatting.userList.UserListFragment;
import com.kj.random_chatting.userMatching.UserMatchingFragment;
import com.kj.random_chatting.userRegist.UserRegistFragment;

public class MainActivity extends FragmentActivity {
    private Fragment matchingFragment, chattingFragment, mapFragment, infoFragment;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Log : " + TAG + " -> onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * 하단 네비게이션 기본 설정 시작
         */
        //List
        matchingFragment = new UserListFragment();
        //유저등록 (임시로 사용)
        chattingFragment = new UserRegistFragment();

        //chattingFragment = new UserMatchingFragment();
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