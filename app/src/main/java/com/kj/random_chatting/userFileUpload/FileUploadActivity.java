package com.kj.random_chatting.userFileUpload;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.kj.random_chatting.R;
import com.kj.random_chatting.databinding.FileUploadActivityBinding;
import com.kj.random_chatting.userRegist.UserRegistDTO;

public class FileUploadActivity extends Activity {
    private FileUploadActivityBinding binding;
    private static final String TAG = "FileUploadActivity";
    private Context context;
    UserRegistDTO.inputDTO userRegistInputDTO;

    FileUploadService fileUploadService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FileUploadActivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        initializeView();
        setListener();
    }

    private void initializeView() {
        context = this;
        //main에서 전달 받은 데이터
        Intent intentMain = getIntent();

        userRegistInputDTO = new UserRegistDTO.inputDTO();
        userRegistInputDTO.setUserName(intentMain.getStringExtra("strUserName"));
        userRegistInputDTO.setGender(intentMain.getStringExtra("strGender"));
        userRegistInputDTO.setAge(intentMain.getStringExtra("strAge"));
        userRegistInputDTO.setPhoneNumber(intentMain.getStringExtra("strPhoneNumber"));

        //이미지 추가
        fileUploadService = new FileUploadService(context, binding);
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
                        startActivityForResult(Intent.createChooser(intent, "이미지를 선택하세요."), Integer.valueOf(v.getTag().toString()));
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

        binding.fileUploadActivityIvUserPicture0.setOnClickListener(Listener);
        binding.fileUploadActivityIvUserPicture1.setOnClickListener(Listener);
        binding.fileUploadActivityIvUserPicture2.setOnClickListener(Listener);
        binding.fileUploadActivityIvUserPicture3.setOnClickListener(Listener);
        binding.fileUploadActivityIvUserPicture4.setOnClickListener(Listener);
        binding.fileUploadActivityIvUserPicture5.setOnClickListener(Listener);
        binding.fileUploadActivityBtnBack.setOnClickListener(Listener);
        binding.fileUploadActivityBtnRegist.setOnClickListener(Listener);
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
