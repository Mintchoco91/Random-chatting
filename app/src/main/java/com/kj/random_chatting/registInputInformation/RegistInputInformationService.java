package com.kj.random_chatting.registInputInformation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.kj.random_chatting.databinding.ActivityOnboardingBinding;
import com.kj.random_chatting.databinding.RegistInputInformationActivityBinding;
import com.kj.random_chatting.login.LoginActivity;
import com.kj.random_chatting.registInputGender.RegistInputGenderActivity;
import com.kj.random_chatting.registPhoneTextCode.RegistPhoneAuthCodeActivity;
import com.kj.random_chatting.userRegist.UserRegistActivity;
import com.kj.random_chatting.util.UtilClass;

import java.util.HashMap;

public class RegistInputInformationService extends Activity {
    private static final String TAG = "RegistInputInformationService";
    private RegistInputInformationActivityBinding binding;
    private Context context;
    private HashMap<String, String> shareData = new HashMap<>();

    public RegistInputInformationService(Context mContext, RegistInputInformationActivityBinding mBinding, HashMap<String, String> mShareData) {
        Log.d(TAG, "Log : " + TAG + " -> RegistInputInformationService");

        context = mContext;
        binding = mBinding;
        shareData = mShareData;
    }

    /**************************************************************
     *  버튼 클릭 이벤트 시작
     **************************************************************/

    public void btnContinueClick() {
        Log.d(TAG, "Log : " + TAG + "btnContinueClick");
        //validation 필요

        Intent intent = new Intent(context, RegistInputGenderActivity.class);
        shareData.put("nickName",binding.registInputInformationActivityEtNickname.getText().toString());
        shareData.put("birthday",binding.registInputInformationActivityEtBirthday.getText().toString());
        intent.putExtra("shareData", shareData);
        context.startActivity(intent);
    }


    /**************************************************************
     *  버튼 클릭 이벤트 끝
     **************************************************************/
}
