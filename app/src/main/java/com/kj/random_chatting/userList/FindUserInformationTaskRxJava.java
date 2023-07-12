package com.kj.random_chatting.userList;

import android.content.Context;
import android.widget.Toast;

import com.kj.random_chatting.databinding.UserListActivityBinding;
import com.kj.random_chatting.util.PreferenceUtil;
import com.kj.random_chatting.util.Retrofit_client;
import com.kj.random_chatting.util.UtilClass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

/**
 * rxJava 형식으로 동기화 처리
 * 유저 정보 검색
 */
public class FindUserInformationTaskRxJava {
    private UserListActivityBinding binding;
    Context context;
    Disposable backgroundTask;

    List<UserListDTO.outputDTO> userList = new ArrayList<>();
    private UserListService userListService;

    private UtilClass utilClass;

    public FindUserInformationTaskRxJava(Context mContext, UserListActivityBinding mBinding, List<UserListDTO.outputDTO> mUserList, UserListService mUserListService) {
        context = mContext;
        binding = mBinding;
        userList = mUserList;
        userListService = mUserListService;
        PreferenceUtil.init(context);
    }

    //결과 처리
    private void resultPost(Integer code) {
        if (code == 0) {
            userListService.showInformation(userList.get(0));
        } else {
            Toast.makeText(context, "조회 실패", Toast.LENGTH_SHORT).show();
        }
    }

    // backgroundTask를 실행하는 메소드. ex) main에서 호출 시 : insertLoginTaskRxjava.runFunc(...params)
    public void runFunc(String... params) {
        backgroundTask = Observable.fromCallable(() -> {
                    return findUserInformation();
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> resultPost(result));
    }

    //유저 정보 조회
    private Integer findUserInformation() throws IOException {
        Integer resultCode = 0;
        String userId = PreferenceUtil.getUserId(null);
        UserListDTO.searchUserInputDTO inputDTO = new UserListDTO.searchUserInputDTO();
        inputDTO.setUserId(userId);

        try {
            Response<List<UserListDTO.outputDTO>> response = Retrofit_client.getApiService(context).searchUserList(inputDTO).execute();

            if (response.isSuccessful()) {
                List<UserListDTO.outputDTO> list = response.body();
                userList.addAll(list);

                // 목록에 값이 하나도 없을 경우 dummy data 추가
                if (userList.isEmpty()) {
                    UserListDTO.outputDTO userListOutput = new UserListDTO.outputDTO();
                    userListOutput.setId("99999999");
                    userListOutput.setNickName("홍길동");
                    userListOutput.setBirthday("19910101");
                    userListOutput.setGender("남");
                    userList.add(userListOutput);
                }

                Collections.shuffle(userList);
            } else {
                // 4XX 또는 5XX 에러
                resultCode = 1;
            }

        } catch (IOException e) {
            // 네트워크 연결 오류
            resultCode = 2;
        }

        return resultCode;
    }
}
