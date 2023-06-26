package com.kj.random_chatting.userChattingRoomList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.kj.random_chatting.R;
import com.kj.random_chatting.databinding.FragmentUserChattingRoomListBinding;
import com.kj.random_chatting.util.RecyclerItem;
import com.kj.random_chatting.util.UtilClass;

import java.util.ArrayList;

public class UserChattingRoomListFragment extends Fragment {
    private static final String TAG = "UserChattingRoomListFragment";
    private FragmentUserChattingRoomListBinding binding;
    private Context context;
    private UserChattingRoomListService userChattingRoomListService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserChattingRoomListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        super.onViewCreated(view, savedInstanceState);
        initializeView();
        setListener();
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }

    private void initializeView() {
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        context = getContext();

       userChattingRoomListService = new UserChattingRoomListService(context, binding);
    }


    private void setListener() {
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        View.OnClickListener Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.fragment_user_chatting_room_list_btn_make_room:
                        userChattingRoomListService.btnMoveRoomCreateClick();
                        break;
                }
            }
        };

        binding.swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                userChattingRoomListService = new UserChattingRoomListService(context, binding);
                /* 업데이트가 끝났음을 알림 */
                binding.swiperefreshlayout.setRefreshing(false);
            }
        });


        binding.fragmentUserChattingRoomListBtnMakeRoom.setOnClickListener(Listener);
    }
}
