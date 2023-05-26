package com.kj.random_chatting.registInputGender;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.kj.random_chatting.R;
import com.kj.random_chatting.common.Enum;
import com.kj.random_chatting.common.SignUpRegistDTO;
import com.kj.random_chatting.databinding.ActivityOnboardingBinding;
import com.kj.random_chatting.databinding.RegistInputGenderActivityBinding;
import com.kj.random_chatting.login.LoginActivity;
import com.kj.random_chatting.registInputPhoto.RegistInputPhotoActivity;
import com.kj.random_chatting.userRegist.UserRegistActivity;
import com.kj.random_chatting.util.UtilClass;

import java.util.HashMap;

public class RegistInputGenderService extends Activity {
    private static final String TAG = "RegistInputGenderService";
    private RegistInputGenderActivityBinding binding;
    private Context context;
    private UtilClass utilClass;
    private Drawable btnFillGreen;
    private Drawable btnEmptygray;
    private Integer colorWhite;
    private Integer colorBlack;
    private Enum.Gender inputGender;
    private SignUpRegistDTO.input intentData = new SignUpRegistDTO.input();

    public RegistInputGenderService(Context mContext, RegistInputGenderActivityBinding mBinding, SignUpRegistDTO.input mIntentData) {
        Log.d(TAG, "Log : " + TAG + " -> OnboardingService");

        context = mContext;
        binding = mBinding;
        intentData = mIntentData;

        initializeService();
    }

    private void initializeService() {
        Log.d(TAG, "Log : " + TAG + " -> initializeService");
        btnFillGreen = context.getDrawable(R.drawable.btn_fill_green);
        btnEmptygray = context.getDrawable(R.drawable.btn_empty_gray);
        colorWhite = context.getColor(R.color.white);
        colorBlack = context.getColor(R.color.black);
    }

    private boolean validation(Enum.Gender inputGender){
        if(inputGender == null){
            Toast.makeText(context, "성별을 선택하세요.", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }
    /**************************************************************
     *  버튼 클릭 이벤트 시작
     **************************************************************/

    public void btnGenderClick(Enum.Gender gender) {
        Log.d(TAG, "Log : " + TAG + "btnGenderClick");

        switch(gender){
            case MAN:
                binding.registInputInformationActivityBtnMan.setBackground(btnFillGreen);
                binding.registInputInformationActivityBtnMan.setTextColor(colorWhite);

                // 그 외 버튼
                binding.registInputInformationActivityBtnWoman.setBackground(btnEmptygray);
                binding.registInputInformationActivityBtnWoman.setTextColor(colorBlack);
                binding.registInputInformationActivityBtnOther.setBackground(btnEmptygray);
                binding.registInputInformationActivityBtnOther.setTextColor(colorBlack);
                inputGender = Enum.Gender.MAN;
                break;
            case WOMAN:
                binding.registInputInformationActivityBtnWoman.setBackground(btnFillGreen);
                binding.registInputInformationActivityBtnWoman.setTextColor(colorWhite);

                // 그 외 버튼
                binding.registInputInformationActivityBtnMan.setBackground(btnEmptygray);
                binding.registInputInformationActivityBtnMan.setTextColor(colorBlack);
                binding.registInputInformationActivityBtnOther.setBackground(btnEmptygray);
                binding.registInputInformationActivityBtnOther.setTextColor(colorBlack);
                inputGender = Enum.Gender.WOMAN;
                break;
            case OTHER:
                binding.registInputInformationActivityBtnOther.setBackground(btnFillGreen);
                binding.registInputInformationActivityBtnOther.setTextColor(colorWhite);

                // 그 외 버튼
                binding.registInputInformationActivityBtnWoman.setBackground(btnEmptygray);
                binding.registInputInformationActivityBtnWoman.setTextColor(colorBlack);
                binding.registInputInformationActivityBtnMan.setBackground(btnEmptygray);
                binding.registInputInformationActivityBtnMan.setTextColor(colorBlack);
                inputGender = Enum.Gender.OTHER;
                break;
        }

    }

    public void btnContinueClick() {
        Log.d(TAG, "Log : " + TAG + "btnContinueClick");
        if(validation(inputGender)){
            Intent intent = new Intent(context, RegistInputPhotoActivity.class);
            intentData.setGender(inputGender);
            intent.putExtra("intentData", intentData);
            context.startActivity(intent);
        }
    }


    /**************************************************************
     *  버튼 클릭 이벤트 끝
     **************************************************************/

}
