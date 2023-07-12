package com.kj.random_chatting.userChattingRoomCreate;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.kj.random_chatting.common.Enum;
import com.kj.random_chatting.common.MainActivity;
import com.kj.random_chatting.userChatting.UserChattingActivity;
import com.kj.random_chatting.util.Retrofit_client;
import com.kj.random_chatting.util.UtilClass;

import java.io.IOException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

public class UserChattingRoomDetailCreateRxJava {
    private static final String TAG = "UserChattingRoomDetailCreateRxJava";
    private Context context;
    private String roomId;
    private String roomName;
    Disposable backgroundTask;

    public UserChattingRoomDetailCreateRxJava(Context mContext) {
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        context = mContext;
    }

    //결과 처리
    private void resultPost(Integer code) {
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        if (code == 0) {
            Intent intentRoomCreate = new Intent(context, UserChattingActivity.class);
            intentRoomCreate.putExtra("roomId", roomId);
            intentRoomCreate.putExtra("roomName", roomName);
            context.startActivity(intentRoomCreate);
            UtilClass.writeLog(TAG,"DB처리 성공", Enum.LogType.D);
        } else {
            Toast.makeText(context, "등록 실패 ! ErrorCode : " + code, Toast.LENGTH_SHORT).show();
        }
    }

    public void createChattingRoomDetailRunFunc(String mRoomId, String mRoomName) {
        backgroundTask = Observable.fromCallable(() -> {
                    return createChattingRoomDetailInformation(mRoomId, mRoomName);
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> resultPost(result));
    }

    private Integer createChattingRoomDetailInformation(String mRoomId, String mRoomName) throws IOException {
        Integer resultCode;

        try {
            roomId = mRoomId;
            roomName = mRoomName;

            //Insert
            UserChattingRoomDetailDTO.ChattingRoomDetailinputDTO detailDto = new UserChattingRoomDetailDTO.ChattingRoomDetailinputDTO();
            detailDto.setUserId(MainActivity.userNickName);
            detailDto.setRoomId(roomId);

            Response<String> response = Retrofit_client.getApiService(context).createChattingRoomDetail(detailDto).execute();
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
            UtilClass.writeLog(TAG, "Network Connection Error!", Enum.LogType.E);
        } catch (Exception e) {
            // 그 외 오류
            resultCode = 3;
            UtilClass.writeLog(TAG, e.toString(), Enum.LogType.E);
        }

        return resultCode;
    }
}
