package com.kj.random_chatting.userChattingRoomCreate;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.kj.random_chatting.databinding.ActivityUserChattingRoomCreateBinding;
import com.kj.random_chatting.databinding.FragmentUserChattingRoomListBinding;
import com.kj.random_chatting.userChatting.UserChattingActivity;
import com.kj.random_chatting.userChattingRoomList.UserChattingRoomListDTO;
import com.kj.random_chatting.userChattingRoomList.UserChattingRoomListService;
import com.kj.random_chatting.userRegist.UserRegistDTO;
import com.kj.random_chatting.util.ChatListRecyclerAdapter;
import com.kj.random_chatting.util.Retrofit_client;
import com.kj.random_chatting.util.UtilClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;

public class UserChattingRoomCreateTaskRxJava {
    private static final String TAG = "UserChattingRoomCreateTaskRxJava";
    private ActivityUserChattingRoomCreateBinding binding;
    private Context context;
    private String roomName;
    private String roomId;
    private UtilClass utilClass;
    Disposable backgroundTask;

    public UserChattingRoomCreateTaskRxJava(Context mContext, ActivityUserChattingRoomCreateBinding mBinding) {
        Log.d(TAG, "Log : " + TAG + " -> UserChattingRoomCreateTaskRxJava");
        context = mContext;
        binding = mBinding;
        utilClass = new UtilClass();
    }

    //결과 처리
    private void resultPost(Integer code) {
        if (code == 0) {
            Intent intentRoomCreate = new Intent(context, UserChattingActivity.class);
            intentRoomCreate.putExtra("roomId", roomId);
            intentRoomCreate.putExtra("roomName", roomName);
            context.startActivity(intentRoomCreate);
        } else {
            Toast.makeText(context, "등록 실패 ! ErrorCode : " + code, Toast.LENGTH_LONG).show();
        }
    }

    public void createChattingRoomRunFunc(String... params) {
        backgroundTask = Observable.fromCallable(() -> {
                    return createChattingRoomInformation();
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> resultPost(result));
    }

    private Integer createChattingRoomInformation() throws IOException {
        Integer resultCode = 0;
        try {
            roomName = binding.activityUserChattingRoomCreateEtRoomName.getText().toString();
            roomId = utilClass.createRandomNumber(6).toString();

            //Insert
            UserChattingRoomCreateDTO.inputDTO dto = new UserChattingRoomCreateDTO.inputDTO();
            dto.setUserId("미구현");
            dto.setRoomId(roomId);
            dto.setRoomName(roomName);
            dto.setIsMovie("1");
            dto.setIsDrive("1");

            Call<String> call = Retrofit_client.getApiService().createChattingRoom(dto);
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
