package com.kj.random_chatting.util;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.kj.random_chatting.UserChattingRoomList.UserChattingRoomListDTO;
import com.kj.random_chatting.UserChattingRoomList.UserChattingRoomListFragment;
import com.kj.random_chatting.UserChattingRoomList.UserChattingRoomListService;
import com.kj.random_chatting.databinding.FragmentUserChattingRoomListBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * 공통으로 사용 할 Util 함수 정의
 */

public class UtilClass {

    private UtilClass utilClass;
    /**
     * textView 스크롤바 넘어갈 시 밑으로 자동 스크롤처리
     * [textView객체].setMovementMethod(new ScrollingMovementMethod()) 선언 후 사용 할 것
     */

    public void scrollBottom(TextView textView) {
        int lineTop = textView.getLayout().getLineTop(textView.getLineCount());
        int scrollY = lineTop - textView.getHeight();
        if (scrollY > 0) {
            textView.scrollTo(0, scrollY);
        } else {
            textView.scrollTo(0, 0);
        }
    }

    public void chatRecyclerViewAddItem(ArrayList<RecyclerItem> mList, String roomId, String roomName) {
        RecyclerItem item = new RecyclerItem();

        item.setRoomId(roomId);
        item.setRoomName(roomName);

        mList.add(item);
    }

    public void chatRecyclerViewCreateList(FragmentUserChattingRoomListBinding binding, List<UserChattingRoomListDTO.outputDTO> roomList, ChatListRecyclerAdapter adapter){
        utilClass = new UtilClass();

        for(UserChattingRoomListDTO.outputDTO target : roomList){
            utilClass.chatRecyclerViewAddItem(UserChattingRoomListFragment.staticRoomList, target.getRoomId(), target.getRoomName());
        }

        ChatListRecyclerAdapter mAdapter = new ChatListRecyclerAdapter(UserChattingRoomListFragment.staticRoomList) ;

        binding.fragmentUserChattingRoomListRecyclerviewList.setAdapter(adapter) ;
        mAdapter.notifyDataSetChanged() ;
    }
}
