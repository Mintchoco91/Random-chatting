package com.kj.random_chatting.common;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.kj.random_chatting.userChatting.UserChattingActivity;
import com.kj.random_chatting.userChatting.UserChattingDTO;
import com.kj.random_chatting.userChatting.UserChattingService;
import com.kj.random_chatting.util.UtilClass;

import javax.annotation.Nullable;

public class ForecdTerminationService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    //어플 강제 종료시 실행할 내용 작성
    @Override
    public void onTaskRemoved(Intent rootIntent){
        UtilClass.writeLog("Error", "onTaskRemoved - 강제 종료 " + rootIntent, Enum.LogType.I);

        try {
            //채팅창 종료 시 이벤트 기재
            UserChattingService userChattingService = new UserChattingService();
            userChattingService.leaveRoom(UserChattingActivity.roomInfo);
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stopSelf();
    }
}
