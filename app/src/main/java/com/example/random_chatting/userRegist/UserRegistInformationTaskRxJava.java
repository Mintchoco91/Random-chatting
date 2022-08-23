package com.example.random_chatting.userRegist;

import android.app.Activity;
import android.content.Context;
import android.widget.Button;
import android.widget.Toast;

import com.example.random_chatting.R;
import com.example.random_chatting.util.Retrofit_client;

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

public class UserRegistInformationTaskRxJava{
    Context fileUploadActivityContext;
    Disposable backgroundTask;
    private String userName, gender, age, phoneNumber;
    private String[] fileNameUri = new String[6];
    Button btnBack, btnRegist;


    public UserRegistInformationTaskRxJava (Context context, String strUserName, String strGender
            , String strAge, String strPhoneNumber, String[] strFileNameUri) {
        fileUploadActivityContext = context;
        userName = strUserName;
        gender = strGender;
        age = strAge;
        phoneNumber = strPhoneNumber;
        fileNameUri = strFileNameUri;

        btnRegist = ((Button) ((Activity) fileUploadActivityContext).findViewById(R.id.file_upload_activity_btn_regist));
        btnBack = ((Button) ((Activity) fileUploadActivityContext).findViewById(R.id.file_upload_activity_btn_back));
    }

    //결과 처리
    private void resultPost(Integer code){
        if(code == 0){
            btnRegist.setEnabled(false);
            btnBack.setEnabled(false);
            Toast.makeText(fileUploadActivityContext, "등록 성공", Toast.LENGTH_LONG).show();
        }else{
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
            Call<String> call = Retrofit_client.getApiService().registDB(
                    "insert"
                    , userName
                    , gender
                    , age
                    , phoneNumber
                    , insertFileNames[0]
                    , insertFileNames[1]
                    , insertFileNames[2]
                    , insertFileNames[3]
                    , insertFileNames[4]
                    , insertFileNames[5]);

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
        }catch(Exception e){
            resultCode = 3;
        }
        return resultCode;
    }

}
