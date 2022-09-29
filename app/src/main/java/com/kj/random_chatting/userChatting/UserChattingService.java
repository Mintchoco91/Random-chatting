package com.kj.random_chatting.userChatting;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.kj.random_chatting.databinding.FragmentUserChattingBinding;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class UserChattingService extends Activity {
    private Socket socket;
    private static final String TAG = "UserChattingService";
    private FragmentUserChattingBinding fragmentUserChattingBinding;
    private Context userChattingServiceContext;
    private final String socketBaseURL = "https://random-chatting-chat-server.herokuapp.com/";

    public UserChattingService(Context context, FragmentUserChattingBinding binding) {
        Log.d(TAG, "Log : " + TAG + " -> UserChattingService");
        userChattingServiceContext = context;
        fragmentUserChattingBinding = binding;

        try {
            socket = IO.socket(socketBaseURL);
            socket.on("msg", onMessage);
            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void btnSendClick(String chatMessage){
        socket.emit("msg", chatMessage);
    }

    private Emitter.Listener onMessage = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String data = (String) args[0];
                    Log.e("get", data);
                }
            });
        }
    };

}
