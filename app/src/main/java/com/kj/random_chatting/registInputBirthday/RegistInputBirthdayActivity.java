package com.kj.random_chatting.registInputBirthday;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.kj.random_chatting.R;
import com.kj.random_chatting.databinding.ActivityOnboardingBinding;

public class RegistInputBirthdayActivity extends Activity {
    private static final String TAG = "RegistPhoneNumberActivity";
    private ActivityOnboardingBinding binding;
    private Context context;
    private RegistInputBirthdayService registInputBirthdayService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        binding = ActivityOnboardingBinding.inflate(getLayoutInflater());
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
        registInputBirthdayService = new RegistInputBirthdayService(context, binding);
    }

    private void setListener() {
        Log.d(TAG, "Log : " + TAG + " -> setListener");
        View.OnClickListener Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.activity_onboarding_tv_login_click_description:
                        registInputBirthdayService.btnLoginClick();
                        break;
                    case R.id.activity_onboarding_btn_regist:
                        registInputBirthdayService.btnRegistClick();
                        break;
                }
            }
        };

        binding.activityOnboardingTvLoginClickDescription.setOnClickListener(Listener);
        binding.activityOnboardingBtnRegist.setOnClickListener(Listener);
    }
}
