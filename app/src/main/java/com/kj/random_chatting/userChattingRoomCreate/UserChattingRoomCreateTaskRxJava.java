package com.kj.random_chatting.userChattingRoomCreate;

import android.content.Context;
import android.widget.Toast;

import com.kj.random_chatting.common.Enum;
import com.kj.random_chatting.common.MainActivity;
import com.kj.random_chatting.databinding.ActivityUserChattingRoomCreateBinding;
import com.kj.random_chatting.util.Retrofit_client;
import com.kj.random_chatting.util.UtilClass;

import java.io.IOException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

public class UserChattingRoomCreateTaskRxJava {
    private static final String TAG = "UserChattingRoomCreateTaskRxJava";
    private ActivityUserChattingRoomCreateBinding binding;
    private Context context;
    private String roomName;
    private String roomId;
    private UtilClass utilClass;
    Disposable backgroundTask;

    public UserChattingRoomCreateTaskRxJava(Context mContext, ActivityUserChattingRoomCreateBinding mBinding) {
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        context = mContext;
        binding = mBinding;
        utilClass = new UtilClass();

    }

    //결과 처리
    private void resultPost(Integer code) {
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        if (code != 0) {
            Toast.makeText(context, "등록 실패 ! ErrorCode : " + code, Toast.LENGTH_SHORT).show();
        }
    }

    public void createChattingRoomRunFunc(String mRoomId, String mRoomName) {
        backgroundTask = Observable.fromCallable(() -> {
                    return createChattingRoomInformation(mRoomId, mRoomName);
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> resultPost(result));
    }

    private Integer createChattingRoomInformation(String mRoomId, String mRoomName) throws IOException {
        Integer resultCode;

        try {
            roomId = mRoomId;
            roomName = mRoomName;

            UserChattingRoomCreateDTO.inputDTO dto = new UserChattingRoomCreateDTO.inputDTO();
            dto.setUserId(MainActivity.userNickName);
            dto.setRoomId(roomId);
            dto.setRoomName(roomName);
            dto.setIsMovie("1");
            dto.setIsDrive("1");

            Response<String> response = Retrofit_client.getApiService(context).createChattingRoom(dto).execute();
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
            // 그 외 에러
            resultCode = 3;
            UtilClass.writeLog(TAG, e.toString(), Enum.LogType.E);
        }

        return resultCode;
    }
}
