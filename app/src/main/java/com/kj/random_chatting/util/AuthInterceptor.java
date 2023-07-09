package com.kj.random_chatting.util;

import android.content.Context;

import androidx.annotation.NonNull;

import com.kj.random_chatting.common.Enum;
import com.kj.random_chatting.login.LoginDTO;
import com.kj.random_chatting.login.LoginRxJava;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private Context context;

    public AuthInterceptor(Context context){
        this.context = context;

    }
    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        PreferenceUtil.init(context);
        String accessToken = PreferenceUtil.getAccessToken("none");
        String refreshToken = PreferenceUtil.getRefreshToken("none");

        // 처음에는 엑세스 토큰을 가지고 인증한다.
        Request original = chain.request().newBuilder().addHeader("Authorization", accessToken).build();
        Response response = chain.proceed(original);

        // access token이 만료된 경우 refresh token을 가지고 다시 검증한다.
        // refresh token도 만료된 경우 로그인 다시 해야됨
        // refresh token의 만료기간이 유효한 경우 access token과 refresh token을 다시 발급받아 저장한다.
        if (response.code() == 401) {
            UtilClass.writeLog("Interceptor", "인증이 만료됨", Enum.LogType.D);

            LoginDTO.input input = new LoginDTO.input();
            input.setEmail(PreferenceUtil.getEmail("none"));
            input.setPassword(PreferenceUtil.getPassword("none"));
            input.setRenewal(true);
            input.setAccessToken(accessToken);
            input.setRefreshToken(refreshToken);
            retrofit2.Response<LoginDTO.output> result = Retrofit_client.getApiService(context).signIn(input).execute();

            if (result.isSuccessful()) {
                // 새로 갱신한 토큰 포함, 사용자 정보를 환경변수에 저장한다.
                LoginRxJava.saveUserInfo(result.body());
                // 갱신한 토큰을 가지고 재요청
                Request newRequest = chain.request().newBuilder().addHeader("Authorization", result.body().getAccessToken()).build();
                return chain.proceed(newRequest);
            }
            return chain.proceed(original.newBuilder().header("Authorization", PreferenceUtil.getAccessToken("none")).build());
        }

        return response;
    }
}