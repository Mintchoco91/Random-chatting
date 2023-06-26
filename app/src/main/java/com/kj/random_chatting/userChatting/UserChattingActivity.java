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
import com.kj.random_chatting.common.Enum;
import com.kj.random_chatting.databinding.FragmentUserChattingBinding;
import com.kj.random_chatting.util.UtilClass;

public class UserChattingActivity extends Activity {
    private static final String TAG = "UserChattingActivity";
    private FragmentUserChattingBinding binding;
    private Context context;
    private UserChattingService userChattingService;
    // 종료를 위해서 static 처리
    public static UserChattingDTO.RoomInfo roomInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentUserChattingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        initializeView();
        setListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        //채팅창 종료 시 이벤트 기재
        userChattingService.leaveRoom(roomInfo);
    }

    private void initializeView() {
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        context = this;

        Intent intentMain = getIntent();
        String roomId = intentMain.getStringExtra("roomId");
        String roomName = intentMain.getStringExtra("roomName");

        roomInfo = new UserChattingDTO.RoomInfo();
        roomInfo.setRoomId(roomId);
        roomInfo.setRoomName(roomName);

        binding.fragmentUserChattingTvChatScreen.setTextColor(Color.BLACK);
        binding.fragmentUserChattingTvChatScreen.setMovementMethod(new ScrollingMovementMethod());
        userChattingService = new UserChattingService();
        userChattingService.createRoom(context, binding, roomInfo);
    }

    private void setListener() {
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
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
