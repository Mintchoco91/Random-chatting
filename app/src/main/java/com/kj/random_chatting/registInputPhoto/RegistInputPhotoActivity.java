package com.kj.random_chatting.registInputPhoto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.kj.random_chatting.R;
import com.kj.random_chatting.databinding.RegistInputInformationActivityBinding;
import com.kj.random_chatting.databinding.RegistInputPhotoActivityBinding;

import java.util.HashMap;

public class RegistInputPhotoActivity extends Activity {
    private static final String TAG = "RegistInputPhotoActivity";
    private RegistInputPhotoActivityBinding binding;
    private Context context;
    private RegistInputPhotoService registInputPhotoService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        binding = RegistInputPhotoActivityBinding.inflate(getLayoutInflater());
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



        registInputPhotoService = new RegistInputPhotoService(context, binding, shareData);
    }

    private void setListener() {
        Log.d(TAG, "Log : " + TAG + " -> setListener");
        View.OnClickListener Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.regist_input_photo_activity_btn_picture1:
                    case R.id.regist_input_photo_activity_btn_picture2:
                    case R.id.regist_input_photo_activity_btn_picture3:
                    case R.id.regist_input_photo_activity_btn_picture4:                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "이미지를 선택하세요."), Integer.valueOf(v.getTag().toString()));
                        break;
                }
            }
        };

        binding.registInputPhotoActivityBtnPicture1.setOnClickListener(Listener);
        binding.registInputPhotoActivityBtnPicture2.setOnClickListener(Listener);
        binding.registInputPhotoActivityBtnPicture3.setOnClickListener(Listener);
        binding.registInputPhotoActivityBtnPicture4.setOnClickListener(Listener);
    }


    //파일 선택 후 결과 처리 (sub class 에서 호출이 안되서 main에 둠.)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            registInputPhotoService.uploadResult(requestCode, resultCode, data);
        }
    }
}
