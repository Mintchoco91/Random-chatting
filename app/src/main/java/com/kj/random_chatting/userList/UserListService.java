package com.kj.random_chatting.userList;

import static android.content.Context.MODE_PRIVATE;
import static com.kj.random_chatting.common.Constants.EMPTY_IMAGE_PATH;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
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
import com.kj.random_chatting.common.Enum;
import com.kj.random_chatting.databinding.UserListActivityBinding;
import com.kj.random_chatting.util.PreferenceUtil;
import com.kj.random_chatting.util.UtilClass;
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
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        context = mContext;
        binding = mBinding;
        PreferenceUtil.init(context);

        //binding.sliderViewPager.setOffscreenPageLimit(1);

        //첫 페이지 loading
        FindUserInformationTaskRxJava findUserInformationTaskRxJava = new FindUserInformationTaskRxJava(context, binding, userList, this);
        findUserInformationTaskRxJava.runFunc();
    }

    public void showInformation(UserListDTO.outputDTO currentInfo) {
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        binding.userListActivityTvNickName.setText(currentInfo.getNickName());
        binding.userListActivityTvAge.setText(currentInfo.getBirthday());
        binding.userListActivityIvPhoto.setTag(currentInfo.getId());

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

    public void btnNextUserClick() {
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
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

    public void btnActionClick(Enum.ActionStatus actionStatus) {
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());

        UserListDTO.matchingInputDTO input  = new UserListDTO.matchingInputDTO();
        String userId = PreferenceUtil.getUserId(null);
        String targetId = binding.userListActivityIvPhoto.getTag().toString();

        input.setUserId(userId);
        input.setTargetId(targetId);
        input.setStatus(String.valueOf(actionStatus));

        RegistMatchingTaskRxJava registMatchingTaskRxJava = new RegistMatchingTaskRxJava(context, binding, this);
        registMatchingTaskRxJava.runFunc(input);


    }
    /**************************************************************
     *  버튼 클릭 이벤트 끝
     **************************************************************/

}
