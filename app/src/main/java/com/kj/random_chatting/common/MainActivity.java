package com.kj.random_chatting.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationBarView;
import com.kj.random_chatting.R;
import com.kj.random_chatting.messenger.MessengerFragment;
import com.kj.random_chatting.showMyInformation.ShowMyInformationFragment;
import com.kj.random_chatting.userChattingRoomList.UserChattingRoomListFragment;
import com.kj.random_chatting.databinding.MainActivityBinding;
import com.kj.random_chatting.userList.UserListFragment;
import com.kj.random_chatting.userLocation.UserLocationFragment;
import com.kj.random_chatting.util.UtilClass;

public class MainActivity extends FragmentActivity {
    private MainActivityBinding binding;
    private FragmentManager fragmentManager;
    private Fragment matchingBulbFragment, chattingListFragment, myRoomListFragment, locationFragment, showMyInformationFragment;
    private static final String TAG = "MainActivity";

    //임시방편 으로 랜덤 닉네임
    public static String userNickName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        super.onCreate(savedInstanceState);
        startService(new Intent(this, ForecdTerminationService.class));

        binding = MainActivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        fragmentManager = getSupportFragmentManager();

        UtilClass utilClass = new UtilClass();
        //임시방편 으로 랜덤 닉네임
        userNickName = "임시계정" + utilClass.createRandomNumber(6).toString();

        /**
         * 하단 네비게이션 기본 설정 시작
         */

        //첫 실행 시 첫번째 fragment 무조건 실행
        matchingBulbFragment = new UserListFragment();
        fragmentManager.beginTransaction().replace(R.id.frameLayout, matchingBulbFragment).commit();

        binding.navView.setOnItemSelectedListener(
                new NavigationBarView.OnItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.navigation_bulb:
                                if (matchingBulbFragment == null) {
                                    //List
                                    matchingBulbFragment = new UserListFragment();
                                    //fragment가 매번 생성될 필요가 없으므로 이미 존재할경우 add로 처리한다.(replace -> add로 변경)
                                    fragmentManager.beginTransaction().add(R.id.frameLayout, matchingBulbFragment).commit();
                                }
                                fragmentManager.beginTransaction().show(matchingBulbFragment).commit();

                                // 다른 프래그먼트들은 가려준다.
                                if (chattingListFragment != null)
                                    fragmentManager.beginTransaction().hide(chattingListFragment).commit();
                                if (locationFragment != null)
                                    fragmentManager.beginTransaction().hide(locationFragment).commit();
                                if (showMyInformationFragment != null)
                                    fragmentManager.beginTransaction().hide(showMyInformationFragment).commit();
                                if (myRoomListFragment != null) {
                                    fragmentManager.beginTransaction().hide(myRoomListFragment).commit();
                                }
                                return true;

                            case R.id.navigation_chatting_list:
                                if (chattingListFragment == null) {
                                    //유저등록 (임시로 사용)
                                    chattingListFragment = new UserChattingRoomListFragment();
                                    fragmentManager.beginTransaction().add(R.id.frameLayout, chattingListFragment).commit();
                                }

                                if (matchingBulbFragment != null)
                                    fragmentManager.beginTransaction().hide(matchingBulbFragment).commit();
                                if (chattingListFragment != null)
                                    fragmentManager.beginTransaction().show(chattingListFragment).commit();
                                if (locationFragment != null)
                                    fragmentManager.beginTransaction().hide(locationFragment).commit();
                                if (showMyInformationFragment != null)
                                    fragmentManager.beginTransaction().hide(showMyInformationFragment).commit();
                                if (myRoomListFragment != null) {
                                    fragmentManager.beginTransaction().hide(myRoomListFragment).commit();
                                }
                                return true;

                            case R.id.navigation_my_room_list:
                                if (myRoomListFragment == null) {
                                    myRoomListFragment = new MessengerFragment();
                                    fragmentManager.beginTransaction().add(R.id.frameLayout, myRoomListFragment).commit();
                                }


                                // 다른 프래그먼트들은 숨긴다.
                                if (matchingBulbFragment != null) {
                                    fragmentManager.beginTransaction().hide(matchingBulbFragment).commit();
                                }
                                if (chattingListFragment != null) {
                                    fragmentManager.beginTransaction().hide(chattingListFragment).commit();
                                }
                                if (locationFragment != null) {
                                    fragmentManager.beginTransaction().hide(locationFragment).commit();
                                }
                                if (showMyInformationFragment != null) {
                                    fragmentManager.beginTransaction().hide(showMyInformationFragment).commit();
                                }
                                if (myRoomListFragment != null) {
                                    fragmentManager.beginTransaction().show(myRoomListFragment).commit();
                                }
                                return true;

                            case R.id.navigation_map:
                                if (locationFragment == null) {
                                    locationFragment = new UserLocationFragment();
                                    fragmentManager.beginTransaction().add(R.id.frameLayout, locationFragment).commit();
                                }

                                if (matchingBulbFragment != null)
                                    fragmentManager.beginTransaction().hide(matchingBulbFragment).commit();
                                if (chattingListFragment != null)
                                    fragmentManager.beginTransaction().hide(chattingListFragment).commit();
                                if (locationFragment != null)
                                    fragmentManager.beginTransaction().show(locationFragment).commit();
                                if (showMyInformationFragment != null)
                                    fragmentManager.beginTransaction().hide(showMyInformationFragment).commit();
                                if (myRoomListFragment != null) {
                                    fragmentManager.beginTransaction().hide(myRoomListFragment).commit();
                                }
                                return true;

                            case R.id.navigation_info:
                                if (showMyInformationFragment == null) {
                                    showMyInformationFragment = new ShowMyInformationFragment();
                                    fragmentManager.beginTransaction().add(R.id.frameLayout, showMyInformationFragment).commit();
                                }

                                if (matchingBulbFragment != null)
                                    fragmentManager.beginTransaction().hide(matchingBulbFragment).commit();
                                if (chattingListFragment != null)
                                    fragmentManager.beginTransaction().hide(chattingListFragment).commit();
                                if (locationFragment != null)
                                    fragmentManager.beginTransaction().hide(locationFragment).commit();
                                if (showMyInformationFragment != null)
                                    fragmentManager.beginTransaction().show(showMyInformationFragment).commit();
                                if (myRoomListFragment != null) {
                                    fragmentManager.beginTransaction().hide(myRoomListFragment).commit();
                                }
                                return true;
                        }
                        return false;
                    }
                }
        );

        binding.navView.setSelectedItemId(R.id.navigation_bulb);

        /**
         * 하단 네비게이션 기본 설정 끝
         */


    }

}