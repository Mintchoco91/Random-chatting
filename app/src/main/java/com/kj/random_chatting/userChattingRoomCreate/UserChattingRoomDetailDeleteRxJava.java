package com.kj.random_chatting.userChattingRoomCreate;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.kj.random_chatting.common.MainActivity;
import com.kj.random_chatting.userChatting.UserChattingActivity;
import com.kj.random_chatting.util.Retrofit_client;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;

public class UserChattingRoomDetailDeleteRxJava {
    private static final String TAG = "UserChattingRoomDetailDeleteRxJava";
    private Context context;
    private String roomId;
    private String roomName;
    Disposable backgroundTask;

    public UserChattingRoomDetailDeleteRxJava() {
        Log.d(TAG, "Log : " + TAG + " -> UserChattingRoomDetailDeleteRxJava");
    }

    //결과 처리
    private void resultPost(Integer code) {
        Log.d(TAG, "Log : " + TAG + " -> resultPost : " + code);
        if (code == 0) {
            Log.d(TAG, "Log : " + TAG + " -> DB처리 성공");
        } else {
            Toast.makeText(context, "방 나가기 실패 ! ErrorCode : " + code, Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteChattingRoomDetailRunFunc(String mRoomId, String mRoomName) {
        backgroundTask = Observable.fromCallable(() -> {
                    return deleteChattingRoomDetailInformation(mRoomId, mRoomName);
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> resultPost(result));
    }

    private Integer deleteChattingRoomDetailInformation(String mRoomId, String mRoomName) throws IOException {
        Integer resultCode = 0;
        try {
            roomId = mRoomId;
            roomName = mRoomName;

            //delete
            UserChattingRoomDetailDTO.ChattingRoomDetailinputDTO detailDto = new UserChattingRoomDetailDTO.ChattingRoomDetailinputDTO();
            detailDto.setUserId(MainActivity.userNickName);
            detailDto.setRoomId(roomId);

            Call<String> call = Retrofit_client.getApiService().deleteChattingRoomDetail(detailDto);
            String jsonResponse = call.execute().body();

            try {
                JSONObject jsonObject = new JSONObject(jsonResponse);
                if (!jsonObject.optString("status").equals("true")) {
                    resultCode = 1;
                }
            } catch (JSONException e) {
                resultCode = 2;
            }

        } catch (Exception e) {
            resultCode = 5;
        }
        return resultCode;
    }
}
