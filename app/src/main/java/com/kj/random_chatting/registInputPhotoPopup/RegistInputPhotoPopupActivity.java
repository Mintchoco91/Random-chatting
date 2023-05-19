package com.kj.random_chatting.registInputPhotoPopup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.kj.random_chatting.R;
import com.kj.random_chatting.databinding.RegistInputPhotoActivityBinding;
import com.kj.random_chatting.databinding.RegistInputPhotoPopupActivityBinding;

import java.util.HashMap;

public class RegistInputPhotoPopupActivity extends Activity {
    private static final String TAG = "RegistInputPhotoPopupActivity";
    private RegistInputPhotoPopupActivityBinding binding;
    private Context context;
    private RegistInputPhotoPopupService registInputPhotoPopupService;
    private String strChoiceNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        binding = RegistInputPhotoPopupActivityBinding.inflate(getLayoutInflater());
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

        //데이터 가져오기
        Intent intent = getIntent();
        strChoiceNumber = intent.getStringExtra("choiceNumber");
        registInputPhotoPopupService = new RegistInputPhotoPopupService(context, binding, strChoiceNumber);
    }

    private void setListener() {
        Log.d(TAG, "Log : " + TAG + " -> setListener");
        View.OnClickListener Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.regist_input_photo_popup_activity_btn_upload_Image:
                        registInputPhotoPopupService.btnUploadClick();
                        break;
                    case R.id.regist_input_photo_popup_activity_btn_delete_image:
                        registInputPhotoPopupService.btnDeleteClick();
                        break;
                    case R.id.regist_input_photo_popup_activity_btn_cancle:
                        registInputPhotoPopupService.btnCancleClick();
                        break;
                }
            }
        };

        binding.registInputPhotoPopupActivityBtnUploadImage.setOnClickListener(Listener);
        binding.registInputPhotoPopupActivityBtnDeleteImage.setOnClickListener(Listener);
        binding.registInputPhotoPopupActivityBtnCancle.setOnClickListener(Listener);
    }
}
