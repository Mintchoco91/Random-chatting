package com.kj.random_chatting.common;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationBarView;
import com.kj.random_chatting.R;
import com.kj.random_chatting.UserChattingRoomList.UserChattingRoomListFragment;
import com.kj.random_chatting.databinding.MainActivityBinding;
import com.kj.random_chatting.userList.UserListFragment;
import com.kj.random_chatting.userInfo.UserInfoFragment;
import com.kj.random_chatting.userLocation.UserLocationFragment;

public class MainActivity extends AppCompatActivity {
    private MainActivityBinding binding;
    private FragmentManager fragmentManager;
    private Fragment matchingFragment, chattingListFragment, locationFragment, infoFragment;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Log : " + TAG + " -> onCreate");
        super.onCreate(savedInstanceState);
        binding = MainActivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        fragmentManager = getSupportFragmentManager();

        /**
         * 하단 네비게이션 기본 설정 시작
         */

        //첫 실행 시 첫번째 fragment 무조건 실행
        matchingFragment = new UserListFragment();
        fragmentManager.beginTransaction().replace(R.id.frameLayout, matchingFragment).commit();

        binding.navView.setOnItemSelectedListener(
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
                                if (chattingListFragment != null)
                                    fragmentManager.beginTransaction().hide(chattingListFragment).commit();
                                if (locationFragment != null)
                                    fragmentManager.beginTransaction().hide(locationFragment).commit();
                                if (infoFragment != null)
                                    fragmentManager.beginTransaction().hide(infoFragment).commit();
                                return true;

                            case R.id.navigation_chatting:
                                if (chattingListFragment == null) {
                                    //유저등록 (임시로 사용)
                                    chattingListFragment = new UserChattingRoomListFragment();
                                    fragmentManager.beginTransaction().add(R.id.frameLayout, chattingListFragment).commit();
                                }

                                if (matchingFragment != null)
                                    fragmentManager.beginTransaction().hide(matchingFragment).commit();
                                if (chattingListFragment != null)
                                    fragmentManager.beginTransaction().show(chattingListFragment).commit();
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
                                if (chattingListFragment != null)
                                    fragmentManager.beginTransaction().hide(chattingListFragment).commit();
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
                                if (chattingListFragment != null)
                                    fragmentManager.beginTransaction().hide(chattingListFragment).commit();
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

        binding.navView.setSelectedItemId(R.id.navigation_matching);

        /**
         * 하단 네비게이션 기본 설정 끝
         */


    }

}