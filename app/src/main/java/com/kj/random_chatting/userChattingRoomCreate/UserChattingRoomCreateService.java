package com.kj.random_chatting.userChattingRoomCreate;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.kj.random_chatting.databinding.ActivityUserChattingRoomCreateBinding;
import com.kj.random_chatting.userChatting.UserChattingActivity;

public class UserChattingRoomCreateService {
    private static final String TAG = "UserChattingRoomCreateService";
    private Context context;
    private ActivityUserChattingRoomCreateBinding binding;

    public UserChattingRoomCreateService(Context mContext, ActivityUserChattingRoomCreateBinding mBinding){
        Log.d(TAG, "Log : " + TAG + " -> UserChattingRoomCreateService");
        context = mContext;
        binding = mBinding;
    }

    /**************************************************************
     *  버튼 클릭 이벤트 시작
     **************************************************************/

    public void btnRoomCreateClick() {
        Log.d(TAG, "Log : " + TAG + "btnMakeRoomClick");

        //DB 방 저장
        UserChattingRoomCreateTaskRxJava userChattingRoomCreateTaskRxJava = new UserChattingRoomCreateTaskRxJava(context, binding);
        userChattingRoomCreateTaskRxJava.createChattingRoomRunFunc();
    }

    /**************************************************************
     *  버튼 클릭 이벤트 끝
     **************************************************************/
}
