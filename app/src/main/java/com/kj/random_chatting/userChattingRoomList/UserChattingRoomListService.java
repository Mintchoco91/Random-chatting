package com.kj.random_chatting.userChattingRoomList;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.kj.random_chatting.databinding.FragmentUserChattingRoomListBinding;
import com.kj.random_chatting.userChattingRoomCreate.UserChattingRoomCreateActivity;
import com.kj.random_chatting.util.ChatListRecyclerAdapter;
import com.kj.random_chatting.util.RecyclerItem;
import com.kj.random_chatting.util.UtilClass;

import java.util.ArrayList;
import java.util.List;

public class UserChattingRoomListService {
    private static final String TAG = "UserChattingRoomListService";
    private Context context;
    private FragmentUserChattingRoomListBinding binding;
    public static List<UserChattingRoomListDTO.outputDTO> mainUserChattingRoomList = new ArrayList<>();


    public UserChattingRoomListService(Context mContext, FragmentUserChattingRoomListBinding mBinding) {
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        context =mContext;
        binding = mBinding;

        UserChattingRoomListTaskRxJava userChattingRoomListTaskRxJava = new UserChattingRoomListTaskRxJava(context, binding);
        userChattingRoomListTaskRxJava.searchChattingRoomRunFunc();
    }

    /**************************************************************
     *  버튼 클릭 이벤트 시작
     **************************************************************/

    public void btnMoveRoomCreateClick() {
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());

        Intent intentMakeRoom = new Intent(context, UserChattingRoomCreateActivity.class);
        context.startActivity(intentMakeRoom);
    }

    /**************************************************************
     *  버튼 클릭 이벤트 끝
     **************************************************************/

}
