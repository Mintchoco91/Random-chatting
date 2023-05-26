package com.kj.random_chatting.registInputEmailPw;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.kj.random_chatting.common.SignUpRegistDTO;
import com.kj.random_chatting.databinding.ActivityOnboardingBinding;
import com.kj.random_chatting.databinding.RegistInputEmailPwActivityBinding;
import com.kj.random_chatting.login.LoginActivity;
import com.kj.random_chatting.registInputGender.RegistInputGenderActivity;
import com.kj.random_chatting.userRegist.UserRegistActivity;
import com.kj.random_chatting.userRegist.UserRegistInformationTaskRxJava;
import com.kj.random_chatting.util.UtilClass;

import java.util.HashMap;

public class RegistInputEmailPwService extends Activity {
    private static final String TAG = "RegistInputEmailPwService";
    private RegistInputEmailPwActivityBinding binding;
    private Context context;
    private SignUpRegistDTO.input intentData = new SignUpRegistDTO.input();

    public RegistInputEmailPwService(Context mContext, RegistInputEmailPwActivityBinding mBinding, SignUpRegistDTO.input mIntentData) {
        Log.d(TAG, "Log : " + TAG + " -> RegistInputEmailPwService");

        context = mContext;
        binding = mBinding;
        intentData = mIntentData;
    }

    private boolean validation(){
        String inputEmail = binding.registInputEmailPwActivityEtEmail.getText().toString();
        String inputPassword = binding.registInputEmailPwActivityEtPassword.getText().toString();
        String inputConfirmPassword = binding.registInputEmailPwActivityEtPassword.getText().toString();

        if(TextUtils.isEmpty(inputEmail)){
            Toast.makeText(context, "이메일을 입력 해 주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(inputPassword)){
            Toast.makeText(context, "비밀번호를 입력 해 주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!inputPassword.equals(inputConfirmPassword)){
            Toast.makeText(context, "입력한 비밀번호과 다릅니다.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private Integer registInformation(){
        RegistInputEmailPwRegistInformationDB registInputEmailPwRegistInformationDB
                = new RegistInputEmailPwRegistInformationDB(context, binding, intentData);

        registInputEmailPwRegistInformationDB.runFunc();
        return 1;
    }

    /**************************************************************
     *  버튼 클릭 이벤트 시작
     **************************************************************/

    public void btnRegistClick() {
        Log.d(TAG, "Log : " + TAG + "btnContinueClick");
        //validation 필요
        if(validation()){
            intentData.setEmail(binding.registInputEmailPwActivityEtEmail.getText().toString());
            intentData.setPassword(binding.registInputEmailPwActivityEtPassword.getText().toString());

            Integer insertId = registInformation();
            if(!TextUtils.isEmpty(insertId.toString())){
                //todo registPictureDB
            }

            Intent intent = new Intent(context, RegistInputGenderActivity.class);
            intent.putExtra("intentData", intentData);
            context.startActivity(intent);
        }
    }



    /**************************************************************
     *  버튼 클릭 이벤트 끝
     **************************************************************/
}
