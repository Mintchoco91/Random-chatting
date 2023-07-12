package com.kj.random_chatting.registInputEmailPw;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.kj.random_chatting.common.Enum;
import com.kj.random_chatting.common.SignUpRegistDTO;
import com.kj.random_chatting.databinding.RegistInputEmailPwActivityBinding;
import com.kj.random_chatting.login.LoginActivity;
import com.kj.random_chatting.util.Retrofit_client;
import com.kj.random_chatting.util.UtilClass;

import java.io.IOException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

public class RegistInputEmailPwRegistPhotoDB {
    private static final String TAG = "RegistInputEmailPwRegistPhotoDB";
    RegistInputEmailPwActivityBinding binding;
    Context context;
    Disposable backgroundTask;

    private SignUpRegistDTO.input intentData = new SignUpRegistDTO.input();

    public RegistInputEmailPwRegistPhotoDB(Context mContext, RegistInputEmailPwActivityBinding mBinding, SignUpRegistDTO.input mIntentData) {
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());

        context = mContext;
        binding = mBinding;
        intentData = mIntentData;
    }

    //결과 처리
    private void resultPost(SignUpRegistDTO.output result) {
        if (result.getResultCode() == 0) {
            binding.registInputEmailPwActivityBtnRegist.setEnabled(true);

            Intent intent = new Intent(context, LoginActivity.class);
            intent.putExtra("intentData", intentData);
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "등록 실패 ! ErrorCode : " + result.getResultCode(), Toast.LENGTH_SHORT).show();
        }
    }

    public void runFunc(String... params) {
        backgroundTask = Observable.fromCallable(() -> {
                    return userRegistPhoto();
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> resultPost(result));
    }

    private SignUpRegistDTO.output userRegistPhoto() {
        SignUpRegistDTO.output result = new SignUpRegistDTO.output();
        Integer resultCode;

        try {
            Response<String> response = Retrofit_client.getApiService(context).signUpPhotoRegist(intentData).execute();
            if (response.isSuccessful()) {
                // insert 성공
                resultCode = 0;
            } else {
                // insert 실패
                resultCode = 1;
                UtilClass.writeLog(TAG, response.errorBody().string(), Enum.LogType.E);
            }
        } catch (IOException e) {
            // 네트워크 연결 오류
            resultCode = 2;
        }

        result.setResultCode(resultCode);
        return result;
    }

}
