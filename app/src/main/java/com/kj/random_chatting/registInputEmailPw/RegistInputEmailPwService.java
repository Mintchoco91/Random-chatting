package com.kj.random_chatting.registInputEmailPw;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.kj.random_chatting.common.SignUpRegistDTO;
import com.kj.random_chatting.databinding.RegistInputEmailPwActivityBinding;
import com.kj.random_chatting.util.UtilClass;

import java.util.HashMap;

public class RegistInputEmailPwService extends Activity {
    private static final String TAG = "RegistInputEmailPwService";
    private RegistInputEmailPwActivityBinding binding;
    private Context context;
    private SignUpRegistDTO.input intentData = new SignUpRegistDTO.input();

    public RegistInputEmailPwService(Context mContext, RegistInputEmailPwActivityBinding mBinding, SignUpRegistDTO.input mIntentData) {
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());

        context = mContext;
        binding = mBinding;
        intentData = mIntentData;
    }

    private boolean validation(){
        String inputEmail = binding.registInputEmailPwActivityEtEmail.getText().toString();
        String inputPassword = binding.registInputEmailPwActivityEtPassword.getText().toString();
        String inputConfirmPassword = binding.registInputEmailPwActivityEtConfirmPassword.getText().toString();

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

    private void registInformation(){
        RegistInputEmailPwRegistInformationDB registInputEmailPwRegistInformationDB
                = new RegistInputEmailPwRegistInformationDB(context, binding, intentData);

        registInputEmailPwRegistInformationDB.runFunc();
    }

    /**************************************************************
     *  버튼 클릭 이벤트 시작
     **************************************************************/

    public void btnRegistClick() {
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        //validation 필요
        if(validation()){
            intentData.setEmail(binding.registInputEmailPwActivityEtEmail.getText().toString());
            intentData.setPassword(binding.registInputEmailPwActivityEtPassword.getText().toString());

            registInformation();
        }
    }



    /**************************************************************
     *  버튼 클릭 이벤트 끝
     **************************************************************/
}
