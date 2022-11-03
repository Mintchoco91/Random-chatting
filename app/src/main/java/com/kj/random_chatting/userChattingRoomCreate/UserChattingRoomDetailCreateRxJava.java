package com.kj.random_chatting.userChattingRoomCreate;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.kj.random_chatting.common.MainActivity;
import com.kj.random_chatting.databinding.ActivityUserChattingRoomCreateBinding;
import com.kj.random_chatting.userChatting.UserChattingActivity;
import com.kj.random_chatting.util.Retrofit_client;
import com.kj.random_chatting.util.UtilClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;

public class UserChattingRoomDetailCreateRxJava {
    private static final String TAG = "UserChattingRoomDetailCreateRxJava";
    private Context context;
    private String roomId;
    private String roomName;
    Disposable backgroundTask;

    public UserChattingRoomDetailCreateRxJava(Context mContext) {
        Log.d(TAG, "Log : " + TAG + " -> UserChattingRoomDetailCreateRxJava");
        context = mContext;
    }

    //결과 처리
    private void resultPost(Integer code) {
        Log.d(TAG, "Log : " + TAG + " -> resultPost : " + code);
        if (code == 0) {
            Intent intentRoomCreate = new Intent(context, UserChattingActivity.class);
            intentRoomCreate.putExtra("roomId", roomId);
            intentRoomCreate.putExtra("roomName", roomName);
            context.startActivity(intentRoomCreate);
        } else {
            Toast.makeText(context, "등록 실패 ! ErrorCode : " + code, Toast.LENGTH_LONG).show();
        }
    }

    public void createChattingRoomDetailRunFunc(String mRoomId, String mRoomName) {
        backgroundTask = Observable.fromCallable(() -> {
                    return createChattingRoomDetailInformation(mRoomId, mRoomName);
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> resultPost(result));
    }

    private Integer createChattingRoomDetailInformation(String mRoomId, String mRoomName) throws IOException {
        Integer resultCode = 0;
        try {
            roomId = mRoomId;
            roomName = mRoomName;

            //Insert
            UserChattingRoomDetailDTO.inputDTO detailDto = new UserChattingRoomDetailDTO.inputDTO();
            detailDto.setUserId(MainActivity.userNickName);
            detailDto.setRoomId(roomId);

            Call<String> call = Retrofit_client.getApiService().createChattingRoomDetail(detailDto);
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
