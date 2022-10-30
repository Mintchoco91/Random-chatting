package com.kj.random_chatting.common;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.kj.random_chatting.userChatting.UserChattingService;

import javax.annotation.Nullable;

public class ForecdTerminationService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent){
        Log.e("Error","onTaskRemoved - 강제 종료 " + rootIntent);
        UserChattingService userChattingService = new UserChattingService();
        userChattingService.leaveRoom();
        stopSelf();
    }

}
