package com.kj.random_chatting.UserChattingRoomList;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.kj.random_chatting.databinding.FragmentUserChattingRoomListBinding;
import com.kj.random_chatting.userList.UserListActivity;

import java.util.ArrayList;
import java.util.List;

public class UserChattingRoomListService {
    private static final String TAG = "UserChattingRoomListService";
    private Context userChattingRoomListServiceContext;
    private FragmentUserChattingRoomListBinding fragmentUserChattingRoomListBinding;
    public static List<UserChattingRoomListDTO.outputDTO> mainUserChattingRoomList = new ArrayList<>();


    public UserChattingRoomListService(Context context, FragmentUserChattingRoomListBinding binding) {
        Log.d(TAG, "Log : " + TAG + " -> UserChattingRoomListService");
        userChattingRoomListServiceContext = context;
        fragmentUserChattingRoomListBinding = binding;
        UserChattingRoomListTaskRxJava userChattingRoomListTaskRxJava = new UserChattingRoomListTaskRxJava(userChattingRoomListServiceContext, fragmentUserChattingRoomListBinding);
        userChattingRoomListTaskRxJava.searchChattingRoomRunFunc();
    }


    /**************************************************************
     *  버튼 클릭 이벤트 시작
     **************************************************************/

    public void btnMakeRoomClick() {
        Log.d(TAG, "Log : " + TAG + "btnMakeRoomClick");
        Intent intentMakeRoom = new Intent(userChattingRoomListServiceContext, UserListActivity.class);
        userChattingRoomListServiceContext.startActivity(intentMakeRoom);
    }

    /**************************************************************
     *  버튼 클릭 이벤트 끝
     **************************************************************/

}
