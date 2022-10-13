package com.kj.random_chatting.userChatting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;

import com.kj.random_chatting.R;
import com.kj.random_chatting.databinding.FragmentUserChattingBinding;
import com.kj.random_chatting.databinding.UserListActivityBinding;
import com.kj.random_chatting.userList.FindUserInformationTaskRxJava;
import com.kj.random_chatting.userList.UserListService;
import com.kj.random_chatting.userRegist.UserRegistDTO;

public class UserChattingActivity extends Activity {
    private static final String TAG = "UserChattingActivity";
    private FragmentUserChattingBinding binding;
    private Context context;
    private UserChattingService userChattingService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentUserChattingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        initializeView();
        setListener();
    }

    private void initializeView() {
        Log.d(TAG, "Log : " + TAG + " -> initializeView");
        context = this;

        Intent intentMain = getIntent();
        String roomId = intentMain.getStringExtra("roomId");
        String roomName = intentMain.getStringExtra("roomName");

        UserChattingDTO.RoomInfo roomInfo = new UserChattingDTO.RoomInfo();
        roomInfo.setRoomId(roomId);
        roomInfo.setRoomName(roomName);

        binding.fragmentUserChattingTvChatScreen.setTextColor(Color.BLACK);
        binding.fragmentUserChattingTvChatScreen.setMovementMethod(new ScrollingMovementMethod());
        userChattingService = new UserChattingService(context, binding, roomInfo);
    }

    private void setListener() {
        Log.d(TAG, "Log : " + TAG + " -> setListener");
        View.OnClickListener Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.fragment_user_chatting_btn_send:
                        String chatMessage = binding.fragmentUserChattingEtMessage.getText().toString();
                        userChattingService.btnSendClick(chatMessage);
                        break;
                }
            }
        };

        binding.fragmentUserChattingBtnSend.setOnClickListener(Listener);
    }
}
