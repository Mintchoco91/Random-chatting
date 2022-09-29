package com.kj.random_chatting.userChatting;

import android.app.Activity;
import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import com.kj.random_chatting.databinding.FragmentUserChattingBinding;

import java.net.URISyntaxException;
import java.util.Random;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class UserChattingService extends Activity {
    private Socket socket;
    private static final String TAG = "UserChattingService";
    private FragmentUserChattingBinding fragmentUserChattingBinding;
    private Context userChattingServiceContext;
    private final String socketBaseURL = "https://random-chatting-chat-server.herokuapp.com/";
    private String tempId = "";

    public UserChattingService(Context context, FragmentUserChattingBinding binding) {
        Log.d(TAG, "Log : " + TAG + " -> UserChattingService");
        userChattingServiceContext = context;
        fragmentUserChattingBinding = binding;

        //임시방편으로 랜덤 닉네임
        Random random = new Random();
        int bound = 999999;
        tempId = "임시계정" + random.nextInt(bound);

        try {
            socket = IO.socket(socketBaseURL);
            socket.on("ServerToClientMsg", onMessage);
            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void btnSendClick(String chatMessage){
        //공백 입력일 경우 서버 전송 안함.
        if(!chatMessage.equals("")) {
            socket.emit("clientToServerMsg", tempId + " : " + chatMessage);
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
        String historyChatText = fragmentUserChattingBinding.fragmentUserChattingTvChatScreen.getText().toString();
        String recentChatText = "";
        //가장 첫 메세지일 경우, 개행 안함
        if (historyChatText != null && !historyChatText.equals("")) {
            recentChatText = "\n" + ServerToClientMsg;
        }else{
            recentChatText = ServerToClientMsg;
        }
        fragmentUserChattingBinding.fragmentUserChattingTvChatScreen.append(recentChatText);
        fragmentUserChattingBinding.fragmentUserChattingEtMessage.setText("");
        scrollBottom(fragmentUserChattingBinding.fragmentUserChattingTvChatScreen);
    }

    //textView 밑으로 자동 스크롤처리
    private void scrollBottom(TextView textView) {
        int lineTop =  textView.getLayout().getLineTop(textView.getLineCount()) ;
        int scrollY = lineTop - textView.getHeight();
        if (scrollY > 0) {
            textView.scrollTo(0, scrollY);
        } else {
            textView.scrollTo(0, 0);
        }
    }

}
