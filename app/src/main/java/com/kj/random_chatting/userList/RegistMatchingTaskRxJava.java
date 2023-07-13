package com.kj.random_chatting.userList;

import android.content.Context;
import android.widget.Toast;

import com.kj.random_chatting.common.Enum;
import com.kj.random_chatting.databinding.UserListActivityBinding;
import com.kj.random_chatting.util.Retrofit_client;
import com.kj.random_chatting.util.UtilClass;

import java.io.IOException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

/**
 * rxJava 형식으로 동기화 처리
 * 매칭 등록
 */
public class RegistMatchingTaskRxJava {
    private static final String TAG = "RegistMatchingTaskRxJava";
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
        Integer resultCode;

        try {
            Response<String> response = Retrofit_client.getApiService(context).registMatching(input).execute();
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
