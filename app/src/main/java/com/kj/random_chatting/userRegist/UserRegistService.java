package com.kj.random_chatting.userRegist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.kj.random_chatting.R;
import com.kj.random_chatting.UserFileUpload.FileUploadActivity;
import com.kj.random_chatting.databinding.UserRegistActivityBinding;
import com.kj.random_chatting.userList.UserListActivity;

public class UserRegistService {
    private UserRegistActivityBinding userRegistActivityBinding;
    private static final String TAG = "UserRegistService";
    private Context userRegistServiceContext;
    private String strUserName, strGender, strAge, strPhoneNumber;

    public UserRegistService(Context context, UserRegistActivityBinding binding) {
        Log.d(TAG, "Log : " + TAG + " -> UserRegistService");
        userRegistServiceContext = context;
        userRegistActivityBinding = binding;
    }

    // 기본사항 validation
    // Error 존재 -> return true
    public boolean validation(UserRegistDTO.inputDTO registInputDTO) {
        Log.d(TAG, "Log : " + TAG + " -> validation");
        if (registInputDTO.getUserName().length() == 0) {
            Toast.makeText(userRegistServiceContext, "이름을 입력하세요.", Toast.LENGTH_SHORT).show();
            userRegistActivityBinding.activityMainEtUserName.requestFocus();
            return true;
        }
        if (registInputDTO.getGender() == null) {
            Toast.makeText(userRegistServiceContext, "성별을 선택하세요.", Toast.LENGTH_SHORT).show();
            userRegistActivityBinding.activityMainBtnGenderMan.requestFocus();
            return true;
        }
        if (registInputDTO.getAge().equals("선택")) {
            Toast.makeText(userRegistServiceContext, "나이를 선택하세요.", Toast.LENGTH_SHORT).show();
            userRegistActivityBinding.activityMainSpnAge.requestFocus();
            return true;
        }
        if (registInputDTO.getPhoneNumber().length() == 0) {
            Toast.makeText(userRegistServiceContext, "핸드폰 번호를 입력하세요.", Toast.LENGTH_SHORT).show();
            userRegistActivityBinding.activityMainEtPhoneNumber.requestFocus();
            return true;
        }
        return false;
    }

    //다음 단계로 (이미지 업로드)
    public void nextStep(UserRegistDTO.inputDTO registInputDTO) {
        Log.d(TAG, "Log : " + TAG + "nextStep");
        Intent intentUpload = new Intent(userRegistServiceContext, FileUploadActivity.class);
        intentUpload.putExtra("strUserName", registInputDTO.getUserName());
        intentUpload.putExtra("strGender", registInputDTO.getGender());
        intentUpload.putExtra("strAge", registInputDTO.getAge());
        intentUpload.putExtra("strPhoneNumber", registInputDTO.getPhoneNumber());

        userRegistServiceContext.startActivity(intentUpload);
    }

    /**************************************************************
     *  버튼 클릭 이벤트 시작
     **************************************************************/

    public void btnGenderClick(String genderText) {
        Log.d(TAG, "Log : " + TAG + "btnGenderClick");
        strGender = genderText;
        switch (strGender) {
            case "남":
                userRegistActivityBinding.activityMainBtnGenderWoman.setSelected(false);
                break;
            case "여":
                userRegistActivityBinding.activityMainBtnGenderMan.setSelected(false);
                break;
            default:
                break;
        }
    }

    public void btnSaveClick() {
        Log.d(TAG, "Log : " + TAG + "btnSaveClick");
        boolean isError = true;
        strUserName = String.valueOf(userRegistActivityBinding.activityMainEtUserName.getText()).trim();
        strAge = userRegistActivityBinding.activityMainSpnAge.getSelectedItem().toString();
        strPhoneNumber = String.valueOf(userRegistActivityBinding.activityMainEtPhoneNumber.getText()).trim();

        UserRegistDTO.inputDTO registInputDTO = new UserRegistDTO.inputDTO();
        registInputDTO.setUserName(strUserName);
        registInputDTO.setAge(strAge);
        registInputDTO.setPhoneNumber(strPhoneNumber);
        registInputDTO.setGender(strGender);

        isError = validation(registInputDTO);

        if (!isError) {
            nextStep(registInputDTO);
        }
    }

    public void btnUploadClick() {
        Log.d(TAG, "Log : " + TAG + "btnUploadClick");
        Intent intentUploadList = new Intent(userRegistServiceContext, UserListActivity.class);
        userRegistServiceContext.startActivity(intentUploadList);
    }

    /**************************************************************
     *  버튼 클릭 이벤트 끝
     **************************************************************/
}
