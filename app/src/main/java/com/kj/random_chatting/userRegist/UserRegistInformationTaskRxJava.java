package com.kj.random_chatting.userRegist;

import android.content.Context;
import android.widget.Toast;

import com.kj.random_chatting.databinding.FileUploadActivityBinding;
import com.kj.random_chatting.util.Retrofit_client;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;

public class UserRegistInformationTaskRxJava {
    private FileUploadActivityBinding fileUploadActivityBinding;
    Context fileUploadActivityContext;
    Disposable backgroundTask;
    private String email, password, userName, gender, age, phoneNumber;
    private String[] fileNameUri = new String[6];


    public UserRegistInformationTaskRxJava(Context context, FileUploadActivityBinding binding, String strEmail, String strPassword, String strUserName, String strGender
            , String strAge, String strPhoneNumber, String[] strFileNameUri) {
        fileUploadActivityContext = context;
        fileUploadActivityBinding = binding;
        email = strEmail;
        password = strPassword;
        userName = strUserName;
        gender = strGender;
        age = strAge;
        phoneNumber = strPhoneNumber;
        fileNameUri = strFileNameUri;
    }

    //결과 처리
    private void resultPost(Integer code) {
        if (code == 0) {
            fileUploadActivityBinding.fileUploadActivityBtnRegist.setEnabled(false);
            fileUploadActivityBinding.fileUploadActivityBtnBack.setEnabled(false);
            Toast.makeText(fileUploadActivityContext, "등록 성공", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(fileUploadActivityContext, "등록 실패 ! ErrorCode : " + code, Toast.LENGTH_LONG).show();
        }
    }

    public void runFunc(String... params) {
        backgroundTask = Observable.fromCallable(() -> {
                    return userRegistInformation();
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> resultPost(result));
    }

    private Integer userRegistInformation() throws IOException {
        Integer resultCode = 0;
        try {
            //빈 Array를 제거하기 위해 list로 변경
            List<String> lstFileName = new ArrayList<>(Arrays.asList(fileNameUri));
            lstFileName.removeAll(Collections.singletonList(null));

            //빈배열 제거한 array
            String[] insertFileNames = new String[6];

            for (int i = 0; i < 6; i++) {
                if (i < lstFileName.size()) {
                    insertFileNames[i] = lstFileName.get(i);
                } else {
                    insertFileNames[i] = null;
                }
            }

            //Insert
            UserRegistDTO.inputDTO dto = new UserRegistDTO.inputDTO();
            dto.setEmail(email);
            dto.setPassword(password);
            dto.setUserName(userName);
            dto.setGender(gender);
            dto.setAge(age);
            dto.setPhoneNumber(phoneNumber);
            dto.setFileName0(insertFileNames[0]);
            dto.setFileName1(insertFileNames[1]);
            dto.setFileName2(insertFileNames[2]);
            dto.setFileName3(insertFileNames[3]);
            dto.setFileName4(insertFileNames[4]);
            dto.setFileName5(insertFileNames[5]);

            Call<String> call = Retrofit_client.getApiService().createUser(dto);
            String jsonResponse = call.execute().body();

            try {
                JSONObject jsonObject = new JSONObject(jsonResponse);
                if (jsonObject.optString("status").equals("true")) {
                    resultCode = 0;
                } else {
                    resultCode = 1;
                }
            } catch (JSONException e) {
                resultCode = 2;
            }
        } catch (Exception e) {
            resultCode = 3;
        }
        return resultCode;
    }

}
