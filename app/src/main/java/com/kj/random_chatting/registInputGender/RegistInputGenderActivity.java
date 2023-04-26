package com.kj.random_chatting.registInputGender;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.kj.random_chatting.R;
import com.kj.random_chatting.databinding.ActivityOnboardingBinding;
import com.kj.random_chatting.databinding.RegistInputGenderActivityBinding;

public class RegistInputGenderActivity extends Activity {
    private static final String TAG = "RegistInputGenderActivity";
    private RegistInputGenderActivityBinding binding;
    private Context context;
    private RegistInputGenderService registInputGenderService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        binding = RegistInputGenderActivityBinding.inflate(getLayoutInflater());
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
        registInputGenderService = new RegistInputGenderService(context, binding);
    }

    private void setListener() {
        Log.d(TAG, "Log : " + TAG + " -> setListener");
        View.OnClickListener Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.regist_input_information_activity_btn_man:
                        registInputGenderService.btnGenderClick(RegistInputGenderDTO.Gender.MAN);
                        break;
                    case R.id.regist_input_information_activity_btn_woman:
                        registInputGenderService.btnGenderClick(RegistInputGenderDTO.Gender.WOMAN);
                        break;
                    case R.id.regist_input_information_activity_btn_other:
                        registInputGenderService.btnGenderClick(RegistInputGenderDTO.Gender.OTHER);
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
