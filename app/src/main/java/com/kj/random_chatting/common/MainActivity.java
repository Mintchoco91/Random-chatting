package com.kj.random_chatting.common;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.kj.random_chatting.R;
import com.kj.random_chatting.userList.UserListFragment;
import com.kj.random_chatting.userChatting.UserChattingFragment;
import com.kj.random_chatting.userInfo.UserInfoFragment;
import com.kj.random_chatting.userLocation.UserLocationFragment;

public class MainActivity extends FragmentActivity {
    private FragmentManager fragmentManager;
    private Fragment matchingFragment, chattingFragment, locationFragment, infoFragment;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Log : " + TAG + " -> onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        fragmentManager = getSupportFragmentManager();

        /**
         * 하단 네비게이션 기본 설정 시작
         */
        BottomNavigationView navView = findViewById(R.id.nav_view);

        //첫 실행 시 첫번째 fragment 무조건 실행
        matchingFragment = new UserListFragment();
        fragmentManager.beginTransaction().replace(R.id.frameLayout, matchingFragment).commit();

        navView.setOnItemSelectedListener(
                new NavigationBarView.OnItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.navigation_matching:
                                if (matchingFragment == null) {
                                    //List
                                    matchingFragment = new UserListFragment();
                                    //fragment가 매번 생성될 필요가 없으므로 이미 존재할경우 add로 처리한다.(replace -> add로 변경)
                                    fragmentManager.beginTransaction().add(R.id.frameLayout, matchingFragment).commit();
                                }

                                if (matchingFragment != null)
                                    fragmentManager.beginTransaction().show(matchingFragment).commit();
                                if (chattingFragment != null)
                                    fragmentManager.beginTransaction().hide(chattingFragment).commit();
                                if (locationFragment != null)
                                    fragmentManager.beginTransaction().hide(locationFragment).commit();
                                if (infoFragment != null)
                                    fragmentManager.beginTransaction().hide(infoFragment).commit();
                                return true;

                            case R.id.navigation_chatting:
                                if (chattingFragment == null) {
                                    //유저등록 (임시로 사용)
                                    chattingFragment = new UserChattingFragment();
                                    fragmentManager.beginTransaction().add(R.id.frameLayout, chattingFragment).commit();
                                }

                                if (matchingFragment != null)
                                    fragmentManager.beginTransaction().hide(matchingFragment).commit();
                                if (chattingFragment != null)
                                    fragmentManager.beginTransaction().show(chattingFragment).commit();
                                if (locationFragment != null)
                                    fragmentManager.beginTransaction().hide(locationFragment).commit();
                                if (infoFragment != null)
                                    fragmentManager.beginTransaction().hide(infoFragment).commit();
                                return true;

                            case R.id.navigation_location:
                                if (locationFragment == null) {
                                    locationFragment = new UserLocationFragment();
                                    fragmentManager.beginTransaction().add(R.id.frameLayout, locationFragment).commit();
                                }

                                if (matchingFragment != null)
                                    fragmentManager.beginTransaction().hide(matchingFragment).commit();
                                if (chattingFragment != null)
                                    fragmentManager.beginTransaction().hide(chattingFragment).commit();
                                if (locationFragment != null)
                                    fragmentManager.beginTransaction().show(locationFragment).commit();
                                if (infoFragment != null)
                                    fragmentManager.beginTransaction().hide(infoFragment).commit();
                                return true;

                            case R.id.navigation_info:
                                if (infoFragment == null) {
                                    infoFragment = new UserInfoFragment();
                                    fragmentManager.beginTransaction().add(R.id.frameLayout, infoFragment).commit();
                                }

                                if (matchingFragment != null)
                                    fragmentManager.beginTransaction().hide(matchingFragment).commit();
                                if (chattingFragment != null)
                                    fragmentManager.beginTransaction().hide(chattingFragment).commit();
                                if (locationFragment != null)
                                    fragmentManager.beginTransaction().hide(locationFragment).commit();
                                if (infoFragment != null)
                                    fragmentManager.beginTransaction().show(infoFragment).commit();
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