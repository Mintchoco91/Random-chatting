package com.kj.random_chatting.userRegist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.kj.random_chatting.R;
import com.kj.random_chatting.UserFileUpload.FileUploadActivity;
import com.kj.random_chatting.userList.UserListActivity;

public class UserRegistService {
    private Context userRegistServiceContext;
    private EditText etUserName, etPhoneNumber;
    private Button btnGenderMan, btnGenderWoman;
    private Spinner spinnerAge;
    private String strUserName, strGender, strAge, strPhoneNumber;


    public UserRegistService(Context context) {
        userRegistServiceContext = context;
        etUserName = ((EditText) ((Activity) userRegistServiceContext).findViewById(R.id.activity_main_et_user_name));
        btnGenderMan = ((Button) ((Activity) userRegistServiceContext).findViewById(R.id.activity_main_btn_gender_man));
        btnGenderWoman = ((Button) ((Activity) userRegistServiceContext).findViewById(R.id.activity_main_btn_gender_woman));
        spinnerAge = ((Spinner) ((Activity) userRegistServiceContext).findViewById(R.id.activity_main_spn_age));
        etPhoneNumber = ((EditText) ((Activity) userRegistServiceContext).findViewById(R.id.activity_main_et_phone_number));
    }

    // 기본사항 validation
    // Error 존재 -> return true
    public boolean validation(UserRegistDTO.inputDTO registInputDTO) {
        if (registInputDTO.getUserName().length() == 0) {
            Toast.makeText(userRegistServiceContext, "이름을 입력하세요.", Toast.LENGTH_SHORT).show();
            etUserName.requestFocus();
            return true;
        }
        if (registInputDTO.getGender() == null) {
            Toast.makeText(userRegistServiceContext, "성별을 선택하세요.", Toast.LENGTH_SHORT).show();
            btnGenderMan.requestFocus();
            return true;
        }
        if (registInputDTO.getAge().equals("선택")) {
            Toast.makeText(userRegistServiceContext, "나이를 선택하세요.", Toast.LENGTH_SHORT).show();
            spinnerAge.requestFocus();
            return true;
        }
        if (registInputDTO.getPhoneNumber().length() == 0) {
            Toast.makeText(userRegistServiceContext, "핸드폰 번호를 입력하세요.", Toast.LENGTH_SHORT).show();
            etPhoneNumber.requestFocus();
            return true;
        }
        return false;
    }

    //다음 단계로 (이미지 업로드)
    public void nextStep(UserRegistDTO.inputDTO registInputDTO) {
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
        strGender = genderText;
        switch (strGender) {
            case "남":
                btnGenderWoman.setSelected(false);
                break;
            case "여":
                btnGenderMan.setSelected(false);
                break;
            default:
                break;
        }
    }

    public void btnSaveClick() {
        boolean isError = true;
        strUserName = String.valueOf(etUserName.getText()).trim();
        strAge = spinnerAge.getSelectedItem().toString();
        strPhoneNumber = String.valueOf(etPhoneNumber.getText()).trim();

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
        Intent intentUploadList = new Intent(userRegistServiceContext, UserListActivity.class);
        userRegistServiceContext.startActivity(intentUploadList);
    }

    /**************************************************************
     *  버튼 클릭 이벤트 끝
     **************************************************************/
}
