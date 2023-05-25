package com.kj.random_chatting.registInputEmailPw;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.kj.random_chatting.common.SignUpRegistDTO;
import com.kj.random_chatting.databinding.FileUploadActivityBinding;
import com.kj.random_chatting.databinding.RegistInputEmailPwActivityBinding;
import com.kj.random_chatting.userRegist.UserRegistDTO;
import com.kj.random_chatting.util.Retrofit_client;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;

public class RegistInputEmailPwRegistInformationDB {
    private static final String TAG = "RegistInputEmailPwRegistInformationDB";
    RegistInputEmailPwActivityBinding binding;
    Context context;
    Disposable backgroundTask;

    private SignUpRegistDTO intentData = new SignUpRegistDTO();

    public RegistInputEmailPwRegistInformationDB(Context mContext, RegistInputEmailPwActivityBinding mBinding, SignUpRegistDTO mIntentData) {
        Log.d(TAG, "Log : " + TAG + " -> RegistInputEmailPwRegistInformationDB");

        context = mContext;
        binding = mBinding;
        intentData = mIntentData;
    }

    //결과 처리
    private void resultPost(Integer code) {
        if (code == 0) {
            binding.registInputEmailPwActivityBtnRegist.setEnabled(false);

            Toast.makeText(context, "등록 성공", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "등록 실패 ! ErrorCode : " + code, Toast.LENGTH_SHORT).show();
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
            Call<String> call = Retrofit_client.getApiService().signUpRegist(intentData);
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
