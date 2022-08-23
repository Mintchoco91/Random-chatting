package com.kj.random_chatting.userList;

import android.app.Activity;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.viewpager2.widget.ViewPager2;

import com.kj.random_chatting.R;
import com.kj.random_chatting.util.ViewPagerClass;
import com.kj.random_chatting.util.ImageSliderAdapter;

public class UserListService {
    Context userListServiceContext;
    private TextView tvName, tvGender, tvAge, tvPhoneNumber;

    //slider
    private ViewPager2 sliderViewPager;
    private LinearLayout layoutIndicator;

    public UserListService (Context context) {
        userListServiceContext = context;
    }

    public void showInformation(UserListDTO.outputDTO info) {
        tvName = ((TextView) ((Activity) userListServiceContext).findViewById(R.id.user_list_activity_tv_name));
        tvGender = ((TextView) ((Activity) userListServiceContext).findViewById(R.id.user_list_activity_tv_gender));
        tvAge = ((TextView) ((Activity) userListServiceContext).findViewById(R.id.user_list_activity_tv_age));
        tvPhoneNumber = ((TextView) ((Activity) userListServiceContext).findViewById(R.id.user_list_activity_tv_phone_number));

        tvName.setText(info.getUserName());
        tvGender.setText(info.getGender());
        tvAge.setText(info.getAge());
        tvPhoneNumber.setText(info.getPhoneNumber());

        if (info.getFileNameList().size() > 0) {
            String[] fileNameArray = info.getFileNameList().toArray(new String[info.getFileNameList().size()]);
            //Slide처리
            showPhoto(userListServiceContext, fileNameArray);
        } else {
            Toast.makeText(userListServiceContext, "이미지가 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    //이미지 slide 추가 해야함.
    public void showPhoto(Context context, String[] fileNameArray) {
        sliderViewPager = ((ViewPager2) ((Activity) userListServiceContext).findViewById(R.id.sliderViewPager));
        layoutIndicator = ((LinearLayout) ((Activity) userListServiceContext).findViewById(R.id.layoutIndicators));

        sliderViewPager.setOffscreenPageLimit(1);
        sliderViewPager.setAdapter(new ImageSliderAdapter(context, fileNameArray));

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

}
