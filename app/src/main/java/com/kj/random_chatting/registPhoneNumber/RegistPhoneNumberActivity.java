package com.kj.random_chatting.registPhoneNumber;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.kj.random_chatting.R;
import com.kj.random_chatting.databinding.ActivityOnboardingBinding;
import com.kj.random_chatting.databinding.RegistPhoneNumberActivityBinding;

public class RegistPhoneNumberActivity extends Activity {
    private static final String TAG = "RegistPhoneNumberActivity";
    private RegistPhoneNumberActivityBinding binding;
    private Context context;
    private RegistPhoneNumberService registPhoneNumberService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = RegistPhoneNumberActivityBinding.inflate(getLayoutInflater());
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
        registPhoneNumberService = new RegistPhoneNumberService(context, binding);
    }

    private void setListener() {
        Log.d(TAG, "Log : " + TAG + " -> setListener");
        View.OnClickListener Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.regist_phone_number_activity_btn_continue:
                        registPhoneNumberService.btnContinueClick();
                        break;
                }
            }
        };

        binding.registPhoneNumberActivityBtnContinue.setOnClickListener(Listener);
    }
}
