package com.kj.random_chatting.login;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.kj.random_chatting.common.Enum;
import com.kj.random_chatting.common.MainActivity;
import com.kj.random_chatting.databinding.LoginActivityBinding;
import com.kj.random_chatting.util.PreferenceUtil;
import com.kj.random_chatting.util.Retrofit_client;
import com.kj.random_chatting.util.UtilClass;

import java.io.IOException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

public class LoginRxJava {
    private static final String TAG = "LoginRxJava";
    private Context context;
    private LoginActivityBinding binding;
    Disposable backgroundTask;

    public LoginRxJava(Context mContext, LoginActivityBinding mBinding) {
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        context = mContext;
        binding = mBinding;
        PreferenceUtil.init(context);
    }

    //결과 처리
    private void resultPost(Integer code) {
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        if (code == 0) {
            UtilClass.writeLog(TAG,"DB처리 성공", Enum.LogType.D);
            Toast.makeText(context, "로그인 성공", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "로그인 실패", Toast.LENGTH_SHORT).show();
        }
    }

    public void loginRunFunc() {
        backgroundTask = Observable.fromCallable(() -> {
                    return login();
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> resultPost(result));
    }

    private Integer login() throws IOException {
        Integer resultCode = 0;
        try {
            String email = binding.loginActivityEtEmail.getText().toString().trim();
            String password = binding.loginActivityEtPassword.getText().toString().trim();

            LoginDTO.input input = new LoginDTO.input();
            input.setEmail(email);
            input.setPassword(password);

            Response<LoginDTO.output> response = Retrofit_client.getApiService(context).signIn(input).execute();

            if (response.isSuccessful()) {
                LoginDTO.output output = response.body();
                saveUserInfo(output);
            } else {
                // select 결과 없음
                resultCode = 1;
                UtilClass.writeLog(TAG, "Log : " + TAG + " -> doLogin/ onFailure_1 : 조회 결과 없음", Enum.LogType.E);
                UtilClass.writeLog(TAG, response.errorBody().string(), Enum.LogType.E);
            }
        } catch (IOException e) {
            // 네트워크 연결 오류
            resultCode = 2;
        } catch (Exception e) {
            // 그 외 오류
            resultCode = 3;
        }
        return resultCode;
    }

    public static void saveUserInfo(LoginDTO.output output) {
        PreferenceUtil.setAccessToken(output.getAccessToken());
        PreferenceUtil.setRefreshToken(output.getRefreshToken());
        PreferenceUtil.setUserId(output.getId());
        PreferenceUtil.setCountryCode(output.getCountryCode());
        PreferenceUtil.setPhoneNumber(output.getPhoneNumber());
        PreferenceUtil.setNickName(output.getNickName());
        PreferenceUtil.setBirthday(output.getBirthday());
        PreferenceUtil.setGender(output.getGender());
        PreferenceUtil.setEmail(output.getEmail());
        PreferenceUtil.setPassword(output.getPassword());
        PreferenceUtil.setPhotoList(output.getPhotoList());
    }
}
