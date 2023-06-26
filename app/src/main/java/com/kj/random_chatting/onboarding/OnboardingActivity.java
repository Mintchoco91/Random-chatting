package com.kj.random_chatting.onboarding;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;

import com.kj.random_chatting.R;
import com.kj.random_chatting.common.Enum;
import com.kj.random_chatting.databinding.ActivityOnboardingBinding;
import com.kj.random_chatting.databinding.FragmentUserChattingBinding;
import com.kj.random_chatting.userChatting.UserChattingDTO;
import com.kj.random_chatting.userChatting.UserChattingService;
import com.kj.random_chatting.util.UtilClass;

public class OnboardingActivity extends Activity {
    private static final String TAG = "OnboardingActivity";
    private ActivityOnboardingBinding binding;
    private Context context;
    private OnboardingService onboardingService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        binding = ActivityOnboardingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        initializeView();
        setListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    private void initializeView() {
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        context = this;
        onboardingService = new OnboardingService(context, binding);
    }

    private void setListener() {
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        View.OnClickListener Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.activity_onboarding_tv_login_click_description:
                        onboardingService.btnLoginClick();
                        break;
                    case R.id.activity_onboarding_btn_regist:
                        onboardingService.btnRegistClick();
                        break;
                }
            }
        };

        binding.activityOnboardingTvLoginClickDescription.setOnClickListener(Listener);
        binding.activityOnboardingBtnRegist.setOnClickListener(Listener);
    }
}
