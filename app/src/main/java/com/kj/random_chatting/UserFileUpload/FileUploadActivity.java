package com.kj.random_chatting.UserFileUpload;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.kj.random_chatting.R;
import com.kj.random_chatting.userRegist.UserRegistDTO;

public class FileUploadActivity extends Activity {
    private static final String TAG = "FileUploadActivity";
    private Button btnBack, btnRegist;
    private Context context;
    private ImageView ivUserPicture0, ivUserPicture1, ivUserPicture2, ivUserPicture3, ivUserPicture4, ivUserPicture5;
    UserRegistDTO.inputDTO userRegistInputDTO;

    FileUploadService fileUploadService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_upload_activity);

        initializeView();
        setListener();
    }

    private void initializeView() {
        context = this;
        String strUserName, strGender, strAge, strPhoneNumber;
        //main에서 전달 받은 데이터
        Intent intentMain = getIntent();
        strUserName = intentMain.getStringExtra("strUserName");
        strGender = intentMain.getStringExtra("strGender");
        strAge = intentMain.getStringExtra("strAge");
        strPhoneNumber = intentMain.getStringExtra("strPhoneNumber");

        userRegistInputDTO = new UserRegistDTO.inputDTO();
        userRegistInputDTO.setUserName(strUserName);
        userRegistInputDTO.setGender(strGender);
        userRegistInputDTO.setAge(strAge);
        userRegistInputDTO.setPhoneNumber(strPhoneNumber);

        btnBack = (Button) findViewById(R.id.file_upload_activity_btn_back);
        btnRegist = (Button) findViewById(R.id.file_upload_activity_btn_regist);

        //이미지 추가
        ivUserPicture0 = (ImageView) findViewById(R.id.file_upload_activity_iv_user_picture0);
        ivUserPicture1 = (ImageView) findViewById(R.id.file_upload_activity_iv_user_picture1);
        ivUserPicture2 = (ImageView) findViewById(R.id.file_upload_activity_iv_user_picture2);
        ivUserPicture3 = (ImageView) findViewById(R.id.file_upload_activity_iv_user_picture3);
        ivUserPicture4 = (ImageView) findViewById(R.id.file_upload_activity_iv_user_picture4);
        ivUserPicture5 = (ImageView) findViewById(R.id.file_upload_activity_iv_user_picture5);
        fileUploadService = new FileUploadService(context);
    }

    private void setListener() {
        View.OnClickListener Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.file_upload_activity_iv_user_picture0:
                    case R.id.file_upload_activity_iv_user_picture1:
                    case R.id.file_upload_activity_iv_user_picture2:
                    case R.id.file_upload_activity_iv_user_picture3:
                    case R.id.file_upload_activity_iv_user_picture4:
                    case R.id.file_upload_activity_iv_user_picture5:
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "이미지를 선택하세요."), 3);
                        break;
                    case R.id.file_upload_activity_btn_back:
                        fileUploadService.btnBackClick();
                        break;
                    case R.id.file_upload_activity_btn_regist:
                        fileUploadService.btnRegistClick(userRegistInputDTO);
                        break;
                }
            }
        };

        ivUserPicture0.setOnClickListener(Listener);
        ivUserPicture1.setOnClickListener(Listener);
        ivUserPicture2.setOnClickListener(Listener);
        ivUserPicture3.setOnClickListener(Listener);
        ivUserPicture4.setOnClickListener(Listener);
        ivUserPicture5.setOnClickListener(Listener);
        btnBack.setOnClickListener(Listener);
        btnRegist.setOnClickListener(Listener);
    }

    //파일 선택 후 결과 처리 (sub class 에서 호출이 안되서 main에 둠.)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            fileUploadService.uploadResult(requestCode, resultCode, data);
        }
    }
}
