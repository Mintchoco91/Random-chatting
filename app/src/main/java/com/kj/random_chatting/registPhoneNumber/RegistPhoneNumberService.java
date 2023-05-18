package com.kj.random_chatting.registPhoneNumber;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.kj.random_chatting.databinding.RegistPhoneNumberActivityBinding;
import com.kj.random_chatting.registPhoneTextCode.RegistPhoneAuthCodeActivity;
import com.kj.random_chatting.util.UtilClass;

import java.util.HashMap;

public class RegistPhoneNumberService extends Activity {
    private static final String TAG = "RegistPhoneNumberService";
    private RegistPhoneNumberActivityBinding binding;
    private Context context;
    private UtilClass utilClass;

    public RegistPhoneNumberService(Context mContext, RegistPhoneNumberActivityBinding mBinding) {
        Log.d(TAG, "Log : " + TAG + " -> RegistPhoneNumberService");

        context = mContext;
        binding = mBinding;
    }

    /**************************************************************
     *  버튼 클릭 이벤트 시작
     **************************************************************/

    public void btnContinueClick() {
        Log.d(TAG, "Log : " + TAG + "btnContinueClick");
        HashMap<String, String> shareData = new HashMap<>();
        shareData.put("countryCode",binding.registPhoneNumberActivityCcpCountryPicker.getSelectedCountryCode());
        shareData.put("phoneNumber",binding.registPhoneNumberActivityEtPhoneNumber.getText().toString());

        Intent intent = new Intent(context, RegistPhoneAuthCodeActivity.class);
        intent.putExtra("shareData", shareData);
        context.startActivity(intent);
    }

    /**************************************************************
     *  버튼 클릭 이벤트 끝
     **************************************************************/
}
