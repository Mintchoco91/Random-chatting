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

    /**
     * LoginDTO.output 을 사용하진 않지만, 추후에 쓸수있어서 DTO 생성함.
     * 로그인 및 Preference 정보 저장
     * @param jsonResponse
     */
    public void loginProcess(String jsonResponse){
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            LoginDTO.output output = new LoginDTO.output();
            String status = jsonObject.optString("status");

            if ("true".equals(status)) {
                // 토큰을 저장한다.
                output.setAccessToken(jsonObject.optString("access_token"));
                output.setRefreshToken(jsonObject.optString("refresh_token"));
                output.setId(jsonObject.optString("id"));
                output.setCountryCode(jsonObject.optString("countryCode"));
                output.setPhoneNumber(jsonObject.optString("phoneNumber"));
                output.setNickName(jsonObject.optString("nickName"));
                output.setBirthday(jsonObject.optString("birthday"));
                output.setGender(jsonObject.optString("gender"));
                output.setEmail(jsonObject.optString("email"));
                output.setPassword(jsonObject.optString("password"));

                PreferenceUtil.setAccessToken(output.getAccessToken());
                PreferenceUtil.setRefreshToken(output.getRefreshToken());
                PreferenceUtil.setUserId(output.getId());
                PreferenceUtil.setCountryCode(output.getCountryCode());
                PreferenceUtil.setPhoneNumber(output.getPhoneNumber());
                PreferenceUtil.setNickName(output.getNickName());
                PreferenceUtil.setBirthday(output.getBirthday());
                PreferenceUtil.setGender(output.getGender());
                PreferenceUtil.setEmail(output.getEmail());
                PreferenceUtil.setPassword(output.getPassword());

                Toast.makeText(context, "로그인 성공", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            } else {
                Toast.makeText(context, "로그인 실패", Toast.LENGTH_SHORT).show();
                UtilClass.writeLog(TAG, "Log : " + TAG + " -> doLogin/ onFailure_1 : 조회 결과 없음", Enum.LogType.E);
            }
        }catch(Exception e){
            Toast.makeText(context, "로그인 실패", Toast.LENGTH_SHORT).show();
            UtilClass.writeLog(TAG, "Log : " + TAG + " -> doLogin/ onFailure_2 : " + e.getMessage(), Enum.LogType.E);
        }
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
    /**************************************************************
     *  버튼 클릭 이벤트 끝
     **************************************************************/
}
