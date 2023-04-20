package com.kj.random_chatting.onboarding;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.kj.random_chatting.common.Constants;
import com.kj.random_chatting.common.MainActivity;
import com.kj.random_chatting.databinding.ActivityOnboardingBinding;
import com.kj.random_chatting.databinding.FragmentUserChattingBinding;
import com.kj.random_chatting.databinding.UserRegistActivityBinding;
import com.kj.random_chatting.login.LoginActivity;
import com.kj.random_chatting.registPhoneNumber.RegistPhoneNumberActivity;
import com.kj.random_chatting.userChatting.UserChattingDTO;
import com.kj.random_chatting.userChattingRoomCreate.UserChattingRoomDetailDeleteRxJava;
import com.kj.random_chatting.userChattingRoomCreate.UserChattingRoomDetailSelectAndDeleteRxJava;
import com.kj.random_chatting.userList.UserListActivity;
import com.kj.random_chatting.userRegist.UserRegistActivity;
import com.kj.random_chatting.util.UtilClass;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class OnboardingService extends Activity {
    private static final String TAG = "OnboardingService";
    private ActivityOnboardingBinding binding;
    private Context context;
    private UtilClass utilClass;

    public OnboardingService(Context mContext, ActivityOnboardingBinding mBinding) {
        Log.d(TAG, "Log : " + TAG + " -> OnboardingService");

        context = mContext;
        binding = mBinding;
    }

    /**************************************************************
     *  버튼 클릭 이벤트 시작
     **************************************************************/

    public void btnLoginClick() {
        Log.d(TAG, "Log : " + TAG + "btnLoginClick");
        Intent intentUploadList = new Intent(context, LoginActivity.class);
        context.startActivity(intentUploadList);
    }

    public void btnRegistClick() {
        Log.d(TAG, "Log : " + TAG + "btnRegistClick");
        Intent intentUploadList = new Intent(context, RegistPhoneNumberActivity.class);
        context.startActivity(intentUploadList);
    }


    /**************************************************************
     *  버튼 클릭 이벤트 끝
     **************************************************************/
}
