package com.kj.random_chatting.registPhoneNumber;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.kj.random_chatting.databinding.ActivityOnboardingBinding;
import com.kj.random_chatting.databinding.RegistPhoneNumberActivityBinding;
import com.kj.random_chatting.login.LoginActivity;
import com.kj.random_chatting.registPhoneTextCode.RegistPhoneTextCodeActivity;
import com.kj.random_chatting.userRegist.UserRegistActivity;
import com.kj.random_chatting.util.UtilClass;

public class RegistPhoneNumberService extends Activity {
    private static final String TAG = "OnboardingService";
    private RegistPhoneNumberActivityBinding binding;
    private Context context;
    private UtilClass utilClass;

    public RegistPhoneNumberService(Context mContext, RegistPhoneNumberActivityBinding mBinding) {
        Log.d(TAG, "Log : " + TAG + " -> OnboardingService");

        context = mContext;
        binding = mBinding;
    }

    /**************************************************************
     *  버튼 클릭 이벤트 시작
     **************************************************************/

    public void btnContinueClick() {
        Log.d(TAG, "Log : " + TAG + "btnContinueClick");
        Intent intentUploadList = new Intent(context, RegistPhoneTextCodeActivity.class);
        intentUploadList.putExtra("countryCode", binding.registPhoneNumberActivityCcpCountryPicker.getSelectedCountryCode());
        intentUploadList.putExtra("phoneNumber", binding.registPhoneNumberActivityEtPhoneNumber.getText());
        context.startActivity(intentUploadList);
    }

    /**************************************************************
     *  버튼 클릭 이벤트 끝
     **************************************************************/
}
