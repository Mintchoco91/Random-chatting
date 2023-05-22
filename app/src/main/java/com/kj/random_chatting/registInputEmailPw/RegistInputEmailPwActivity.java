package com.kj.random_chatting.registInputEmailPw;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.kj.random_chatting.R;
import com.kj.random_chatting.databinding.ActivityOnboardingBinding;
import com.kj.random_chatting.databinding.RegistInputEmailPwActivityBinding;

import java.util.HashMap;

public class RegistInputEmailPwActivity extends Activity {
    private static final String TAG = "RegistInputEmailPwActivity";
    private RegistInputEmailPwActivityBinding binding;
    private Context context;
    private RegistInputEmailPwService registInputEmailPwService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        binding = RegistInputEmailPwActivityBinding.inflate(getLayoutInflater());
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
        HashMap<String, String> shareData = new HashMap<String, String>();
        Intent intent = getIntent();
        shareData = (HashMap<String, String>) intent.getSerializableExtra("shareData");

        registInputEmailPwService = new RegistInputEmailPwService(context, binding, shareData);
    }

    private void setListener() {
        Log.d(TAG, "Log : " + TAG + " -> setListener");
        View.OnClickListener Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.regist_input_photo_activity_btn_continue:
                        registInputEmailPwService.btnContinueClick();
                        break;
                }
            }
        };

        binding.registInputPhotoActivityBtnContinue.setOnClickListener(Listener);
    }
}
