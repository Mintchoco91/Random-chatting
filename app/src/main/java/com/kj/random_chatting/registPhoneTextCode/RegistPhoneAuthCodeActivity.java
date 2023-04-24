package com.kj.random_chatting.registPhoneTextCode;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.kj.random_chatting.R;
import com.kj.random_chatting.databinding.RegistPhoneAuthCodeActivityBinding;

import java.util.Timer;
import java.util.TimerTask;

public class RegistPhoneAuthCodeActivity extends Activity {
    private static final String TAG = "RegistPhoneAuthCodeActivity";
    private RegistPhoneAuthCodeActivityBinding binding;
    private Context context;
    private RegistPhoneAuthCodeService registPhoneAuthCodeService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = RegistPhoneAuthCodeActivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        initializeView();
        setListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    private void initializeView() {
        Log.d(TAG, "Log : " + TAG + " -> initializeView");
        context = this;
        registPhoneAuthCodeService = new RegistPhoneAuthCodeService(context, binding);
    }

    private void setListener() {
        Log.d(TAG, "Log : " + TAG + " -> setListener");
        View.OnClickListener Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.regist_phone_auth_code_activity_btn_keypad_1:
                        registPhoneAuthCodeService.btnKeypadClick("1");
                        break;
                    case R.id.regist_phone_auth_code_activity_iv_keypad_del:
                        registPhoneAuthCodeService.btndeleteClick();
                        break;
                }
            }
        };

        //binding.activityOnboardingTvLoginClickDescription.setOnClickListener(Listener);
        //binding.activityOnboardingBtnRegist.setOnClickListener(Listener);
    }

}
