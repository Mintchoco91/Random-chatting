package com.kj.random_chatting.userChatting;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.kj.random_chatting.util.UtilClass;
import com.kj.random_chatting.databinding.FragmentUserChattingBinding;

import java.net.URISyntaxException;
import java.util.Random;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class UserChattingService extends Activity {
    private UtilClass utilClass;

    private Socket socket;
    private static final String TAG = "UserChattingService";
    private FragmentUserChattingBinding binding;
    private Context context;
    private final String socketBaseURL = "https://random-chatting-chat-server.herokuapp.com/";
    private String userNickName = "";
    String roomId;
    String roomName;


    public UserChattingService(Context mContext, FragmentUserChattingBinding mBinding, UserChattingDTO.RoomInfo mRoomInfo) {
        Log.d(TAG, "Log : " + TAG + " -> UserChattingService");
        utilClass = new UtilClass();
        context = mContext;
        binding = mBinding;
        roomId = mRoomInfo.getRoomId();
        roomName = mRoomInfo.getRoomName();

        //임시방편 으로 랜덤 닉네임
        userNickName = "임시계정" + utilClass.createRandomNumber(6).toString();;

        try {
            socket = IO.socket(socketBaseURL);
            socket.connect();

            //방생성
            socket.emit("joinRoom", roomId, userNickName);

            //메세지 Listener
            socket.on("ServerToClientMsg", onMessage);

            String firstMsg = "* ["+ roomName + "] 방에 접속하였습니다. - 방 ID : " + roomId;
            binding.fragmentUserChattingTvChatScreen.setText(firstMsg);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void btnSendClick(String chatMessage){
        //공백 입력일 경우 서버 전송 안함.
        if(!chatMessage.equals("")) {
            // param -> 방제, 메세지
            socket.emit("clientToServerMsg", roomId, userNickName + " : " + chatMessage);
        }
    }

    private Emitter.Listener onMessage = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String ServerToClientMsg = (String) args[0];
                    refreshChatScreen(ServerToClientMsg);
                    Log.e("ServerToClientMsg : ", ServerToClientMsg);
                }
            });
        }
    };

    //서버에서 받은 메세지 표기
    private void refreshChatScreen(String ServerToClientMsg){
        String historyChatText = binding.fragmentUserChattingTvChatScreen.getText().toString();
        //두번째 줄 부터 개행 처리
        if (historyChatText != null && !historyChatText.equals("")) {
            binding.fragmentUserChattingTvChatScreen.append("\n");
        }
        binding.fragmentUserChattingTvChatScreen.append(ServerToClientMsg);
        binding.fragmentUserChattingEtMessage.setText("");
        utilClass.scrollBottom(binding.fragmentUserChattingTvChatScreen);
    }
}
