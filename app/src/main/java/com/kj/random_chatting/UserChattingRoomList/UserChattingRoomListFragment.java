package com.kj.random_chatting.UserChattingRoomList;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.kj.random_chatting.R;
import com.kj.random_chatting.databinding.FragmentUserChattingRoomListBinding;

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
        Log.d(TAG, "Log : " + TAG + " -> onViewCreated");
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
        Log.d(TAG, "Log : " + TAG + " -> initializeView");
        context = getContext();
        binding.fragmentUserChattingRoomListTvList.setTextColor(Color.BLACK);
        binding.fragmentUserChattingRoomListTvList.setMovementMethod(new ScrollingMovementMethod());
        userChattingRoomListService = new UserChattingRoomListService(context, binding);
    }

    private void setListener() {
        Log.d(TAG, "Log : " + TAG + " -> setListener");
        View.OnClickListener Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.fragment_user_chatting_room_list_btn_make_room:
                        userChattingRoomListService.btnMakeRoomClick();
                        break;
                }
            }
        };

        binding.fragmentUserChattingRoomListBtnMakeRoom.setOnClickListener(Listener);
    }
}
