package com.kj.random_chatting.userList;

import android.content.Context;
import android.widget.Toast;

import com.kj.random_chatting.databinding.UserListActivityBinding;
import com.kj.random_chatting.util.Retrofit_client;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;

/**
 * rxJava 형식으로 동기화 처리
 * 매칭 등록
 */
public class RegistMatchingTaskRxJava {
    private UserListActivityBinding binding;
    Context context;
    Disposable backgroundTask;

    private UserListService userListService;

    public RegistMatchingTaskRxJava(Context mContext, UserListActivityBinding mBinding, UserListService mUserListService) {
        context = mContext;
        binding = mBinding;
        userListService = mUserListService;
    }

    //결과 처리
    private void resultPost(Integer code) {
        if (code == 0) {
            userListService.btnNextUserClick();
        } else {
            Toast.makeText(context, "매칭 등록 실패", Toast.LENGTH_SHORT).show();
        }
    }

    // backgroundTask를 실행하는 메소드. ex) main에서 호출 시 : insertLoginTaskRxjava.runFunc(...params)
    public void runFunc(UserListDTO.matchingInputDTO input) {
        backgroundTask = Observable.fromCallable(() -> {
                    return registMatching(input);
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> resultPost(result));
    }

    //유저 정보 조회
    private Integer registMatching(UserListDTO.matchingInputDTO input) throws IOException {
        Integer resultCode = 0;
        Call<String> call = Retrofit_client.getApiService(context).registMatching(input);

        //동기화 해야 해서 excute() 처리함.
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
        return resultCode;
    }
}
