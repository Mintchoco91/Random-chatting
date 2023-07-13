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

public class UserChattingRoomDetailSelectAndDeleteRxJava {
    private static final String TAG = "UserChattingRoomDetailSelectAndDeleteRxJava";
    private Context context;
    private String roomId;
    private String roomName;
    Disposable backgroundTask;

    public UserChattingRoomDetailSelectAndDeleteRxJava() {
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    //결과 처리
    private void resultPost(Integer code) {
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        if (code == 0) {
            UtilClass.writeLog(TAG,"DB처리 성공", Enum.LogType.D);
        } else {
            Toast.makeText(context, "빈방 삭제 실패 ! ErrorCode : " + code, Toast.LENGTH_SHORT).show();
        }
    }

    public void selectAndDeleteChattingRoomDetailRunFunc(String mRoomId, String mRoomName) {
        backgroundTask = Observable.fromCallable(() -> {
                    return selectAndDeleteChattingRoomDetailInformation(mRoomId, mRoomName);
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> resultPost(result));
    }

    private Integer selectAndDeleteChattingRoomDetailInformation(String mRoomId, String mRoomName) throws IOException {
        Integer resultCode;

        roomId = mRoomId;
        roomName = mRoomName;

        try {
            UserChattingRoomDetailDTO.ChattingRoomDetailinputDTO detailDto = new UserChattingRoomDetailDTO.ChattingRoomDetailinputDTO();
            detailDto.setUserId(MainActivity.userNickName);
            detailDto.setRoomId(roomId);

            Response<String> response = Retrofit_client.getApiService(context).selectAndDeleteChattingRoomDetail(detailDto).execute();
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
        }

        return resultCode;
    }
}
