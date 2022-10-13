package com.kj.random_chatting.userChattingRoomCreate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;

import com.kj.random_chatting.R;
import com.kj.random_chatting.databinding.ActivityUserChattingRoomCreateBinding;
import com.kj.random_chatting.databinding.FragmentUserChattingBinding;
import com.kj.random_chatting.userChatting.UserChattingService;

public class UserChattingRoomCreateActivity extends Activity {
    private static final String TAG = "UserChattingRoomCreateActivity";
    private ActivityUserChattingRoomCreateBinding binding;
    private Context context;
    private UserChattingRoomCreateService userChattingRoomCreateService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserChattingRoomCreateBinding.inflate(getLayoutInflater());
        context = this;
        View view = binding.getRoot();
        setContentView(view);

        initializeView();
        setListener();
    }

    private void initializeView() {
        Log.d(TAG, "Log : " + TAG + " -> initializeView");

        userChattingRoomCreateService = new UserChattingRoomCreateService(context, binding);
    }

    private void setListener() {
        Log.d(TAG, "Log : " + TAG + " -> setListener");
        View.OnClickListener Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.activity_user_chatting_room_create_btn_room_create:
                        userChattingRoomCreateService.btnRoomCreateClick();
                        break;
                }
            }
        };

        binding.activityUserChattingRoomCreateBtnRoomCreate.setOnClickListener(Listener);
    }
}
