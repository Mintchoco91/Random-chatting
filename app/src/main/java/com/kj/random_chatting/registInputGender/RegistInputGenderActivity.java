package com.kj.random_chatting.registInputGender;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.kj.random_chatting.R;
import com.kj.random_chatting.common.Enum;
import com.kj.random_chatting.common.SignUpRegistDTO;
import com.kj.random_chatting.databinding.ActivityOnboardingBinding;
import com.kj.random_chatting.databinding.RegistInputGenderActivityBinding;
import com.kj.random_chatting.util.UtilClass;

import java.util.HashMap;

public class RegistInputGenderActivity extends Activity {
    private static final String TAG = "RegistInputGenderActivity";
    private RegistInputGenderActivityBinding binding;
    private Context context;
    private RegistInputGenderService registInputGenderService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        binding = RegistInputGenderActivityBinding.inflate(getLayoutInflater());
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
        Intent intent = getIntent();
        SignUpRegistDTO.input intentData = (SignUpRegistDTO.input) intent.getSerializableExtra("intentData");
        registInputGenderService = new RegistInputGenderService(context, binding, intentData);
    }

    private void setListener() {
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        View.OnClickListener Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.regist_input_information_activity_btn_man:
                        registInputGenderService.btnGenderClick(Enum.Gender.MAN);
                        break;
                    case R.id.regist_input_information_activity_btn_woman:
                        registInputGenderService.btnGenderClick(Enum.Gender.WOMAN);
                        break;
                    case R.id.regist_input_information_activity_btn_other:
                        registInputGenderService.btnGenderClick(Enum.Gender.OTHER);
                        break;
                    case R.id.regist_input_information_activity_btn_continue:
                        registInputGenderService.btnContinueClick();
                        break;

                }
            }
        };

        binding.registInputInformationActivityBtnMan.setOnClickListener(Listener);
        binding.registInputInformationActivityBtnWoman.setOnClickListener(Listener);
        binding.registInputInformationActivityBtnOther.setOnClickListener(Listener);
        binding.registInputInformationActivityBtnContinue.setOnClickListener(Listener);
    }
}
