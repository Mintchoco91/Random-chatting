package com.kj.random_chatting.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.kj.random_chatting.common.Enum;
import com.kj.random_chatting.common.MainActivity;
import com.kj.random_chatting.common.SignUpRegistDTO;
import com.kj.random_chatting.databinding.LoginActivityBinding;
import com.kj.random_chatting.databinding.RegistPhoneNumberActivityBinding;
import com.kj.random_chatting.registInputGender.RegistInputGenderActivity;
import com.kj.random_chatting.registPhoneNumber.RegistPhoneNumberActivity;
import com.kj.random_chatting.registPhoneTextCode.RegistPhoneAuthCodeActivity;
import com.kj.random_chatting.util.PreferenceUtil;
import com.kj.random_chatting.util.UtilClass;

import org.json.JSONObject;

public class LoginService extends Activity {
    private static final String TAG = "LoginService";
    private LoginActivityBinding binding;
    private Context context;
    private UtilClass utilClass;
    private SignUpRegistDTO.input intentData = new SignUpRegistDTO.input();

    public LoginService(Context mContext, LoginActivityBinding mBinding) {
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());

        context = mContext;
        binding = mBinding;
    }

    /**************************************************************
     *  버튼 클릭 이벤트 시작
     **************************************************************/

    public void btnRegistClick() {
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());

        Intent intent = new Intent(context, RegistPhoneNumberActivity.class);
        context.startActivity(intent);
    }

    public void btnFindInformationClick() {
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());

        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public void doLogin(){
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());

        LoginRxJava loginRxJava = new LoginRxJava(context, binding);
        loginRxJava.loginRunFunc();
    }
    /**************************************************************
     *  버튼 클릭 이벤트 끝
     **************************************************************/
}
