package com.kj.random_chatting.userChatting;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.kj.random_chatting.common.Constants;
import com.kj.random_chatting.common.MainActivity;
import com.kj.random_chatting.userChattingRoomCreate.UserChattingRoomCreateTaskRxJava;
import com.kj.random_chatting.userChattingRoomCreate.UserChattingRoomDetailCreateRxJava;
import com.kj.random_chatting.userChattingRoomCreate.UserChattingRoomDetailDeleteRxJava;
import com.kj.random_chatting.userChattingRoomCreate.UserChattingRoomDetailSelectAndDeleteRxJava;
import com.kj.random_chatting.util.UtilClass;
import com.kj.random_chatting.databinding.FragmentUserChattingBinding;

import java.net.URISyntaxException;
import java.util.Random;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class UserChattingService extends Activity {
    private final String socketBaseURL = Constants.AWS_NODE_SERVER_IP_ADDRESS;
    private static final String TAG = "UserChattingService";
    private static Socket socket;
    // 종료를 위해서 static 처리
    private String roomId;
    private String roomName;
    private FragmentUserChattingBinding binding;
    private Context context;
    private String userNickName = "";
    private UtilClass utilClass;


    public UserChattingService() {
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        //임시
        userNickName = MainActivity.userNickName;

        try {
            if(socket == null) {
                socket = IO.socket(socketBaseURL);
                socket.connect();
            }

            //메세지 Listener
            socket.on("ServerToClientMsg", onMessage);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void createRoom(Context mContext, FragmentUserChattingBinding mBinding, UserChattingDTO.RoomInfo mRoomInfo){
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        utilClass = new UtilClass();
        context = mContext;
        binding = mBinding;
        roomId = mRoomInfo.getRoomId();
        roomName = mRoomInfo.getRoomName();

        //방생성
        socket.emit("joinRoom", roomId, userNickName);

        String firstMsg = "* ["+ roomName + "] 방에 접속하였습니다. - 방 ID : " + roomId;
        binding.fragmentUserChattingTvChatScreen.setText(firstMsg);
    }

    public void leaveRoom(UserChattingDTO.RoomInfo mRoomInfo){
        // 1. 나간 인원 chatting_room_info_detail 테이블 삭제
        UserChattingRoomDetailDeleteRxJava userChattingRoomDetailDeleteRxJava = new UserChattingRoomDetailDeleteRxJava();
        userChattingRoomDetailDeleteRxJava.deleteChattingRoomDetailRunFunc(mRoomInfo.getRoomId(), mRoomInfo.getRoomName());
        // 2. 해당 방에 남은 인원 있는지 체크 후 인원 없는 방들 삭제
        UserChattingRoomDetailSelectAndDeleteRxJava userChattingRoomDetailSelectAndDeleteRxJava = new UserChattingRoomDetailSelectAndDeleteRxJava();
        userChattingRoomDetailSelectAndDeleteRxJava.selectAndDeleteChattingRoomDetailRunFunc(mRoomInfo.getRoomId(), mRoomInfo.getRoomName());
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

    /**************************************************************
     *  버튼 클릭 이벤트 시작
     **************************************************************/

    public void btnSendClick(String chatMessage){
        //공백 입력일 경우 서버 전송 안함.
        if(!chatMessage.equals("")) {
            // param -> 방제, 메세지
            socket.emit("clientToServerMsg", roomId, userNickName + " : " + chatMessage);
        }
    }

    /**************************************************************
     *  버튼 클릭 이벤트 끝
     **************************************************************/
}
