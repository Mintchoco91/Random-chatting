package com.kj.random_chatting.UserChattingRoomList;

import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.kj.random_chatting.databinding.FragmentUserChattingRoomListBinding;
import com.kj.random_chatting.util.ChatListRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class UserChattingRoomListService {
    private static final String TAG = "UserChattingRoomListService";
    private Context userChattingRoomListServiceContext;
    private FragmentUserChattingRoomListBinding fragmentUserChattingRoomListBinding;
    private ChatListRecyclerAdapter chatListRecyclerAdapter;
    public static List<UserChattingRoomListDTO.outputDTO> mainUserChattingRoomList = new ArrayList<>();

    public UserChattingRoomListService(Context context, FragmentUserChattingRoomListBinding binding, ChatListRecyclerAdapter adapter) {
        Log.d(TAG, "Log : " + TAG + " -> UserChattingRoomListService");
        userChattingRoomListServiceContext = context;
        fragmentUserChattingRoomListBinding = binding;
        chatListRecyclerAdapter = adapter;

        UserChattingRoomListTaskRxJava userChattingRoomListTaskRxJava = new UserChattingRoomListTaskRxJava(userChattingRoomListServiceContext, fragmentUserChattingRoomListBinding, chatListRecyclerAdapter);
        userChattingRoomListTaskRxJava.searchChattingRoomRunFunc();
    }


    /**************************************************************
     *  버튼 클릭 이벤트 시작
     **************************************************************/

    public void btnMakeRoomClick() {
        Log.d(TAG, "Log : " + TAG + "btnMakeRoomClick");

        //UserChattingRoomListTaskRxJava userChattingRoomListTaskRxJava = new UserChattingRoomListTaskRxJava(userChattingRoomListServiceContext, fragmentUserChattingRoomListBinding);
        //userChattingRoomListTaskRxJava.searchChattingRoomRunFunc();
        //Intent intentMakeRoom = new Intent(userChattingRoomListServiceContext, UserListActivity.class);
        //userChattingRoomListServiceContext.startActivity(intentMakeRoom);
    }

    /**************************************************************
     *  버튼 클릭 이벤트 끝
     **************************************************************/

}
