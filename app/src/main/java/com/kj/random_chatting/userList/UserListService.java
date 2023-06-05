package com.kj.random_chatting.userList;

import static com.kj.random_chatting.common.Constants.EMPTY_IMAGE_PATH;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.kj.random_chatting.R;
import com.kj.random_chatting.databinding.UserListActivityBinding;
import com.kj.random_chatting.util.ViewPagerClass;
import com.kj.random_chatting.util.ImageSliderAdapter;

import java.util.ArrayList;
import java.util.List;

public class UserListService {
    private static final String TAG = "UserListService";
    private Context context;
    private UserListActivityBinding binding;
    private List<UserListDTO.outputDTO> userList = new ArrayList<>();

    //page (1페이지 부터 시작)
    private Integer currentPageCnt = 1;

    public UserListService(Context mContext, UserListActivityBinding mBinding) {
        Log.d(TAG, "Log : " + TAG + " -> UserListService");
        context = mContext;
        binding = mBinding;
        //binding.sliderViewPager.setOffscreenPageLimit(1);

        FindUserInformationTaskRxJava findUserInformationTaskRxJava = new FindUserInformationTaskRxJava(context, binding, userList, this);
        findUserInformationTaskRxJava.runFunc();
    }

    public void showInformation(UserListDTO.outputDTO currentInfo) {
        Log.d(TAG, "Log : " + TAG + " -> showInformation");
        binding.userListActivityTvNickName.setText(currentInfo.getNickName() + ", " + currentInfo.getBirthday());
        binding.userListActivityTvJob.setText("Job contents");
        String[] photoNameArray = new String[1];

        if(TextUtils.isEmpty(currentInfo.getPhotoName())){
            photoNameArray[0] = EMPTY_IMAGE_PATH;
        }else{
            photoNameArray[0] = currentInfo.getPhotoName();
        }
        showPhoto(photoNameArray);
    }


    public void showPhoto(String[] fileNameArray) {
        Glide.with(context)
                .load(fileNameArray[0])
                .transform(new CenterCrop(), new RoundedCorners(20))
                .into(binding.userListActivityIvPhoto);
    }

    private void btnNextUserClick() {
        Log.d(TAG, "Log : " + TAG + " -> btnNextUserClick");
        if (currentPageCnt < userList.size()) {
            showInformation(userList.get(currentPageCnt));
            currentPageCnt++;
        } else {
            Toast.makeText(context, "더 이상 회원이 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }
    /* ViewPagerClass 사용시 코드 backup
    public void showPhoto(String[] fileNameArray) {
        Log.d(TAG, "Log : " + TAG + " -> showPhoto");
        binding.sliderViewPager.setAdapter(new ImageSliderAdapter(context, fileNameArray));

        ViewPagerClass viewPagerClass = new ViewPagerClass(context, binding.layoutIndicators);
        binding.sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                viewPagerClass.setCurrentIndicator(position);
            }
        });

        viewPagerClass.setupIndicators(fileNameArray.length);
    }
     */

    /**************************************************************
     *  버튼 클릭 이벤트 시작
     **************************************************************/

    public void btnDisLikeClick() {
        Log.d(TAG, "Log : " + TAG + " -> btnDisLikeClick");
        btnNextUserClick();
    }

    public void btnLikeClick() {
        Log.d(TAG, "Log : " + TAG + " -> btnLikeClick");
        btnNextUserClick();
    }

    public void btnSuperLikeClick() {
        Log.d(TAG, "Log : " + TAG + " -> btnSuperLikeClick");
        btnNextUserClick();
    }
    /**************************************************************
     *  버튼 클릭 이벤트 끝
     **************************************************************/

}
