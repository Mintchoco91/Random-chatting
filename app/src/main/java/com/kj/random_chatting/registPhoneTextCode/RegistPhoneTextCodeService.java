package com.kj.random_chatting.registPhoneTextCode;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.kj.random_chatting.databinding.ActivityOnboardingBinding;
import com.kj.random_chatting.databinding.RegistPhoneTextCodeActivityBinding;
import com.kj.random_chatting.login.LoginActivity;
import com.kj.random_chatting.userRegist.UserRegistActivity;
import com.kj.random_chatting.util.UtilClass;

public class RegistPhoneTextCodeService extends Activity {
    private static final String TAG = "OnboardingService";
    private RegistPhoneTextCodeActivityBinding binding;
    private Context context;
    private UtilClass utilClass;

    public RegistPhoneTextCodeService(Context mContext, RegistPhoneTextCodeActivityBinding mBinding) {
        Log.d(TAG, "Log : " + TAG + " -> OnboardingService");

        context = mContext;
        binding = mBinding;
    }

    /**************************************************************
     *  버튼 클릭 이벤트 시작
     **************************************************************/

    public void btnLoginClick() {
        Log.d(TAG, "Log : " + TAG + "btnLoginClick");
        Intent intentUploadList = new Intent(context, LoginActivity.class);
        context.startActivity(intentUploadList);
    }

    public void btnRegistClick() {
        Log.d(TAG, "Log : " + TAG + "btnRegistClick");
        Intent intentUploadList = new Intent(context, UserRegistActivity.class);
        context.startActivity(intentUploadList);
    }


    /**************************************************************
     *  버튼 클릭 이벤트 끝
     **************************************************************/
}
