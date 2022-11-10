package com.kj.random_chatting.userChattingRoomCreate;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.kj.random_chatting.common.MainActivity;
import com.kj.random_chatting.util.Retrofit_client;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;

public class UserChattingRoomDetailSelectAndDeleteRxJava {
    private static final String TAG = "UserChattingRoomDetailSelectAndDeleteRxJava";
    private Context context;
    private String roomId;
    private String roomName;
    Disposable backgroundTask;

    public UserChattingRoomDetailSelectAndDeleteRxJava() {
        Log.d(TAG, "Log : " + TAG + " -> UserChattingRoomDetailSelectAndDeleteRxJava");
    }

    //결과 처리
    private void resultPost(Integer code) {
        Log.d(TAG, "Log : " + TAG + " -> resultPost : " + code);
        if (code == 0) {
            Log.d(TAG, "Log : " + TAG + " -> DB처리 성공");
        } else {
            Toast.makeText(context, "빈방 삭제 실패 ! ErrorCode : " + code, Toast.LENGTH_LONG).show();
        }
    }

    public void selectAndDeleteChattingRoomDetailRunFunc(String mRoomId, String mRoomName) {
        backgroundTask = Observable.fromCallable(() -> {
                    return selectAndDeleteChattingRoomDetailInformation(mRoomId, mRoomName);
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> resultPost(result));
    }

    private Integer selectAndDeleteChattingRoomDetailInformation(String mRoomId, String mRoomName) throws IOException {
        Integer resultCode = 0;
        try {
            roomId = mRoomId;
            roomName = mRoomName;

            //delete
            UserChattingRoomDetailDTO.ChattingRoomDetailinputDTO detailDto = new UserChattingRoomDetailDTO.ChattingRoomDetailinputDTO();
            detailDto.setUserId(MainActivity.userNickName);
            detailDto.setRoomId(roomId);

            Call<String> call = Retrofit_client.getApiService().selectAndDeleteChattingRoomDetail(detailDto);
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
