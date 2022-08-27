package com.kj.random_chatting.userList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.viewpager2.widget.ViewPager2;

import com.kj.random_chatting.R;
import com.kj.random_chatting.util.ViewPagerClass;
import com.kj.random_chatting.util.ImageSliderAdapter;

import java.util.ArrayList;
import java.util.List;

public class UserListService {
    private static final String TAG = "UserListFragment";
    public static List<UserListDTO.outputDTO> mainUserList = new ArrayList<>();
    Context userListServiceContext;
    private TextView tvName, tvGender, tvAge, tvPhoneNumber;

    //slider
    private ViewPager2 sliderViewPager;
    private LinearLayout layoutIndicator;

    //page (1페이지 부터 시작)
    private Integer currentPageCnt = 1;

    public UserListService(Context context) {
        Log.d(TAG, "Log : " + TAG + " -> UserListService");
        userListServiceContext = context;
        tvName = ((TextView) ((Activity) userListServiceContext).findViewById(R.id.user_list_activity_tv_name));
        tvGender = ((TextView) ((Activity) userListServiceContext).findViewById(R.id.user_list_activity_tv_gender));
        tvAge = ((TextView) ((Activity) userListServiceContext).findViewById(R.id.user_list_activity_tv_age));
        tvPhoneNumber = ((TextView) ((Activity) userListServiceContext).findViewById(R.id.user_list_activity_tv_phone_number));
        sliderViewPager = ((ViewPager2) ((Activity) userListServiceContext).findViewById(R.id.sliderViewPager));
        layoutIndicator = ((LinearLayout) ((Activity) userListServiceContext).findViewById(R.id.layoutIndicators));

        sliderViewPager.setOffscreenPageLimit(1);
    }

    public void showInformation(UserListDTO.outputDTO info) {
        Log.d(TAG, "Log : " + TAG + " -> showInformation");
        tvName.setText(info.getUserName());
        tvGender.setText(info.getGender());
        tvAge.setText(info.getAge());
        tvPhoneNumber.setText(info.getPhoneNumber());

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
        sliderViewPager.setAdapter(new ImageSliderAdapter(userListServiceContext, fileNameArray));

        ViewPagerClass viewPagerClass = new ViewPagerClass(userListServiceContext, layoutIndicator);
        sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
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
