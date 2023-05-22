package com.kj.random_chatting.registInputEmailPw;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.kj.random_chatting.databinding.ActivityOnboardingBinding;
import com.kj.random_chatting.databinding.RegistInputEmailPwActivityBinding;
import com.kj.random_chatting.login.LoginActivity;
import com.kj.random_chatting.registInputGender.RegistInputGenderActivity;
import com.kj.random_chatting.userRegist.UserRegistActivity;
import com.kj.random_chatting.util.UtilClass;

import java.util.HashMap;

public class RegistInputEmailPwService extends Activity {
    private static final String TAG = "RegistInputEmailPwService";
    private RegistInputEmailPwActivityBinding binding;
    private HashMap<String, String> shareData = new HashMap<>();
    private Context context;
    private UtilClass utilClass;

    public RegistInputEmailPwService(Context mContext, RegistInputEmailPwActivityBinding mBinding, HashMap<String, String> mShareData) {
        Log.d(TAG, "Log : " + TAG + " -> RegistInputEmailPwService");

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
        intent.putExtra("shareData", shareData);
        context.startActivity(intent);
    }



    /**************************************************************
     *  버튼 클릭 이벤트 끝
     **************************************************************/
}
