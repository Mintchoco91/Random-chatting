package com.kj.random_chatting.userChattingRoomCreate;

import android.content.Context;
import android.widget.Toast;

import com.kj.random_chatting.common.Enum;
import com.kj.random_chatting.common.MainActivity;
import com.kj.random_chatting.util.Retrofit_client;
import com.kj.random_chatting.util.UtilClass;

import java.io.IOException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

public class UserChattingRoomDetailDeleteRxJava {
    private static final String TAG = "UserChattingRoomDetailDeleteRxJava";
    private Context context;
    private String roomId;
    private String roomName;
    Disposable backgroundTask;

    public UserChattingRoomDetailDeleteRxJava() {
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    //결과 처리
    private void resultPost(Integer code) {
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        if (code == 0) {
            UtilClass.writeLog(TAG,"DB처리 성공", Enum.LogType.D);
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
        Integer resultCode;

        try {
            roomId = mRoomId;
            roomName = mRoomName;

            //delete
            UserChattingRoomDetailDTO.ChattingRoomDetailinputDTO detailDto = new UserChattingRoomDetailDTO.ChattingRoomDetailinputDTO();
            detailDto.setUserId(MainActivity.userNickName);
            detailDto.setRoomId(roomId);

            Response<String> response = Retrofit_client.getApiService(context).deleteChattingRoomDetail(detailDto).execute();
            if (response.isSuccessful()) {
                // delete 성공
                resultCode = 0;
            } else {
                // delete 실패
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
