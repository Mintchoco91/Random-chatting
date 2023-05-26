package com.kj.random_chatting.registPhoneTextCode;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.kj.random_chatting.R;
import com.kj.random_chatting.common.SignUpRegistDTO;
import com.kj.random_chatting.databinding.RegistPhoneAuthCodeActivityBinding;

import java.util.HashMap;
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
        Log.d(TAG, "onCreate: ");
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

        Intent intent = getIntent();
        SignUpRegistDTO.input intentData = (SignUpRegistDTO.input) intent.getSerializableExtra("intentData");

        registPhoneAuthCodeService = new RegistPhoneAuthCodeService(context, binding, intentData);
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
                    case R.id.regist_phone_auth_code_activity_btn_keypad_2:
                        registPhoneAuthCodeService.btnKeypadClick("2");
                        break;
                    case R.id.regist_phone_auth_code_activity_btn_keypad_3:
                        registPhoneAuthCodeService.btnKeypadClick("3");
                        break;
                    case R.id.regist_phone_auth_code_activity_btn_keypad_4:
                        registPhoneAuthCodeService.btnKeypadClick("4");
                        break;
                    case R.id.regist_phone_auth_code_activity_btn_keypad_5:
                        registPhoneAuthCodeService.btnKeypadClick("5");
                        break;
                    case R.id.regist_phone_auth_code_activity_btn_keypad_6:
                        registPhoneAuthCodeService.btnKeypadClick("6");
                        break;
                    case R.id.regist_phone_auth_code_activity_btn_keypad_7:
                        registPhoneAuthCodeService.btnKeypadClick("7");
                        break;
                    case R.id.regist_phone_auth_code_activity_btn_keypad_8:
                        registPhoneAuthCodeService.btnKeypadClick("8");
                        break;
                    case R.id.regist_phone_auth_code_activity_btn_keypad_9:
                        registPhoneAuthCodeService.btnKeypadClick("9");
                        break;
                    case R.id.regist_phone_auth_code_activity_btn_keypad_0:
                        registPhoneAuthCodeService.btnKeypadClick("0");
                        break;
                    case R.id.regist_phone_auth_code_activity_iv_keypad_del:
                        registPhoneAuthCodeService.btnDeleteClick();
                        break;
                    case R.id.regist_phone_auth_code_activity_tv_resend_auth_code:
                        registPhoneAuthCodeService.tvResendClick();
                        break;

                }
            }
        };

        //keypad
        binding.registPhoneAuthCodeActivityBtnKeypad1.setOnClickListener(Listener);
        binding.registPhoneAuthCodeActivityBtnKeypad2.setOnClickListener(Listener);
        binding.registPhoneAuthCodeActivityBtnKeypad3.setOnClickListener(Listener);
        binding.registPhoneAuthCodeActivityBtnKeypad4.setOnClickListener(Listener);
        binding.registPhoneAuthCodeActivityBtnKeypad5.setOnClickListener(Listener);
        binding.registPhoneAuthCodeActivityBtnKeypad6.setOnClickListener(Listener);
        binding.registPhoneAuthCodeActivityBtnKeypad7.setOnClickListener(Listener);
        binding.registPhoneAuthCodeActivityBtnKeypad8.setOnClickListener(Listener);
        binding.registPhoneAuthCodeActivityBtnKeypad9.setOnClickListener(Listener);
        binding.registPhoneAuthCodeActivityBtnKeypad0.setOnClickListener(Listener);

        binding.registPhoneAuthCodeActivityIvKeypadDel.setOnClickListener(Listener);
        binding.registPhoneAuthCodeActivityTvResendAuthCode.setOnClickListener(Listener);

    }

}
