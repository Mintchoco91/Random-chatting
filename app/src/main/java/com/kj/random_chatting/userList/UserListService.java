package com.kj.random_chatting.userList;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.viewpager2.widget.ViewPager2;

import com.kj.random_chatting.R;
import com.kj.random_chatting.databinding.UserListActivityBinding;
import com.kj.random_chatting.util.ViewPagerClass;
import com.kj.random_chatting.util.ImageSliderAdapter;

import java.util.ArrayList;
import java.util.List;

public class UserListService {
    private UserListActivityBinding userListActivityBinding;
    private static final String TAG = "UserListFragment";
    public static List<UserListDTO.outputDTO> mainUserList = new ArrayList<>();
    Context userListServiceContext;

    //page (1페이지 부터 시작)
    private Integer currentPageCnt = 1;

    public UserListService(Context context, UserListActivityBinding binding) {
        Log.d(TAG, "Log : " + TAG + " -> UserListService");
        userListServiceContext = context;
        userListActivityBinding = binding;
        userListActivityBinding.sliderViewPager.setOffscreenPageLimit(1);
    }

    public void showInformation(UserListDTO.outputDTO info) {
        Log.d(TAG, "Log : " + TAG + " -> showInformation");
        userListActivityBinding.userListActivityTvName.setText(info.getUserName());
        userListActivityBinding.userListActivityTvGender.setText(info.getGender());
        userListActivityBinding.userListActivityTvAge.setText(info.getAge());
        userListActivityBinding.userListActivityTvPhoneNumber.setText(info.getPhoneNumber());

        if (info.getFileNameList().size() > 0) {
            String[] fileNameArray = info.getFileNameList().toArray(new String[info.getFileNameList().size()]);
            //Slide처리
            showPhoto(fileNameArray);
        } else {
            //이미지 없는 회원 noImage 처리
            List<String> emptyImage = new ArrayList<>();
            emptyImage.add("https://firebasestorage.googleapis.com/v0/b/random-chatting-b52bc.appspot.com/o/etc%2Fno_image.png?alt=media&token=b62b692a-6a40-49f7-a44f-3ff8c6cb41fa");
            info.setFileNameList(emptyImage);
            String[] fileNameArray = info.getFileNameList().toArray(new String[info.getFileNameList().size()]);
            showPhoto(fileNameArray);
        }
    }

    public void showPhoto(String[] fileNameArray) {
        Log.d(TAG, "Log : " + TAG + " -> showPhoto");
        userListActivityBinding.sliderViewPager.setAdapter(new ImageSliderAdapter(userListServiceContext, fileNameArray));

        ViewPagerClass viewPagerClass = new ViewPagerClass(userListServiceContext, userListActivityBinding.layoutIndicators);
        userListActivityBinding.sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                viewPagerClass.setCurrentIndicator(position);
            }
        });

        viewPagerClass.setupIndicators(fileNameArray.length);
    }

    /**************************************************************
     *  버튼 클릭 이벤트 시작
     **************************************************************/

    public void btnNextUserClick() {
        Log.d(TAG, "Log : " + TAG + " -> btnNextUserClick");
        if (currentPageCnt < mainUserList.size()) {
            showInformation(mainUserList.get(currentPageCnt));
            currentPageCnt++;
        } else {
            Toast.makeText(userListServiceContext, "더 이상 회원이 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    /**************************************************************
     *  버튼 클릭 이벤트 끝
     **************************************************************/

}
