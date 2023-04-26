package com.kj.random_chatting.registInputEmailPw;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.kj.random_chatting.R;
import com.kj.random_chatting.databinding.ActivityOnboardingBinding;

public class RegistInputEmailPwActivity extends Activity {
    private static final String TAG = "RegistPhoneNumberActivity";
    private ActivityOnboardingBinding binding;
    private Context context;
    private RegistInputEmailPwService registInputEmailPwService;

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
        registInputEmailPwService = new RegistInputEmailPwService(context, binding);
    }

    private void setListener() {
        Log.d(TAG, "Log : " + TAG + " -> setListener");
        View.OnClickListener Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.activity_onboarding_tv_login_click_description:
                        registInputEmailPwService.btnLoginClick();
                        break;
                    case R.id.activity_onboarding_btn_regist:
                        registInputEmailPwService.btnRegistClick();
                        break;
                }
            }
        };

        binding.activityOnboardingTvLoginClickDescription.setOnClickListener(Listener);
        binding.activityOnboardingBtnRegist.setOnClickListener(Listener);
    }
}
