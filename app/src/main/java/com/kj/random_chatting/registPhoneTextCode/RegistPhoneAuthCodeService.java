package com.kj.random_chatting.registPhoneTextCode;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.kj.random_chatting.R;
import com.kj.random_chatting.databinding.RegistPhoneAuthCodeActivityBinding;
import com.kj.random_chatting.login.LoginActivity;
import com.kj.random_chatting.util.UtilClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RegistPhoneAuthCodeService extends Activity {
    private static final String TAG = "RegistPhoneAuthCodeService";
    private RegistPhoneAuthCodeActivityBinding binding;
    private Context context;
    private UtilClass utilClass;
    private Integer authIndex;  // 인증번호 현재 index
    private Drawable drawableFocus;
    private Drawable drawableInput;  // 인증번호 입력 시 drawable
    private Integer colorWhite; // 인증번호 입력 시 color


    private Drawable drawableDelete;  // 인증번호 삭제 시 drawable
    private Integer colorGray; // 인증번호 삭제 시 color

    private String authCode; // 인증번호
    private List<String> inputAuthCodeList; // 입력 받은 인증번호
    TimerTask timerTask;
    Timer timer = new Timer();
    public RegistPhoneAuthCodeService(Context mContext, RegistPhoneAuthCodeActivityBinding mBinding) {
        Log.d(TAG, "Log : " + TAG + " -> RegistPhoneAuthCodeService");
        //test data
        authCode = "0000";

        authIndex = 1;
        context = mContext;
        binding = mBinding;

        initializeService();
    }

    private void initializeService() {
        Log.d(TAG, "Log : " + TAG + " -> initializeService");

        inputAuthCodeList = new ArrayList<>();
        startTimer();
        drawableFocus = context.getDrawable(R.drawable.shape_focus_auth);
        drawableInput = context.getDrawable(R.drawable.shape_input_auth);
        colorWhite = context.getColor(R.color.white);

        drawableDelete = context.getDrawable(R.drawable.image_phone_text_rectangle);
        colorGray = context.getColor(R.color.gray);

        binding.registPhoneAuthCodeActivityTvCode1.setBackground(drawableFocus);
    }


    /**************************************************************
     *  버튼 클릭 이벤트 시작
     **************************************************************/

    private void inputKeypad(String inputNumber){
        TextView tvInputCurrentFocus = null;
        TextView tvInputNextFocus = null;
        
        switch(authIndex){
            case 1:
                tvInputCurrentFocus = binding.registPhoneAuthCodeActivityTvCode1;
                tvInputNextFocus = binding.registPhoneAuthCodeActivityTvCode2;
                break;
            case 2:
                tvInputCurrentFocus = binding.registPhoneAuthCodeActivityTvCode2;
                tvInputNextFocus = binding.registPhoneAuthCodeActivityTvCode3;
                break;
            case 3:
                tvInputCurrentFocus = binding.registPhoneAuthCodeActivityTvCode3;
                tvInputNextFocus = binding.registPhoneAuthCodeActivityTvCode4;
                break;
            case 4:
                tvInputCurrentFocus = binding.registPhoneAuthCodeActivityTvCode4;
                break;
        }

        tvInputCurrentFocus.setText(inputNumber);
        tvInputCurrentFocus.setBackground(drawableInput);
        tvInputCurrentFocus.setTextColor(colorWhite);

        // 인증번호 검증
        if(authIndex == 4){
            inputAuthCodeList.add(binding.registPhoneAuthCodeActivityTvCode1.getText().toString());
            inputAuthCodeList.add(binding.registPhoneAuthCodeActivityTvCode2.getText().toString());
            inputAuthCodeList.add(binding.registPhoneAuthCodeActivityTvCode3.getText().toString());
            inputAuthCodeList.add(binding.registPhoneAuthCodeActivityTvCode4.getText().toString());

            if(authCode.equals(inputAuthCodeList.get(0).toString() + inputAuthCodeList.get(1).toString() + inputAuthCodeList.get(2).toString() + inputAuthCodeList.get(3).toString())){
                //go next page
                Toast.makeText(context, "인증번호가 일치합니다!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, "인증번호가 틀립니다.", Toast.LENGTH_SHORT).show();
            }

            initializeAllAuthTextView();
        }else {
            focusInitializeAuthTextView(tvInputNextFocus);
            authIndex ++;
        }
    }


    private void deleteKeypad(){
        TextView tvDeleteCurrentFocus = null;
        TextView tvDeleteNextFocus = null;

        switch(authIndex) {
            case 1:
                //nothing
                tvDeleteNextFocus = binding.registPhoneAuthCodeActivityTvCode1;
                break;
            case 2:
                tvDeleteCurrentFocus = binding.registPhoneAuthCodeActivityTvCode2;
                tvDeleteNextFocus = binding.registPhoneAuthCodeActivityTvCode1;
                break;
            case 3:
                tvDeleteCurrentFocus = binding.registPhoneAuthCodeActivityTvCode3;
                tvDeleteNextFocus = binding.registPhoneAuthCodeActivityTvCode2;
                break;
            case 4:
                tvDeleteCurrentFocus = binding.registPhoneAuthCodeActivityTvCode4;
                tvDeleteNextFocus = binding.registPhoneAuthCodeActivityTvCode3;
                break;
        }

        if(authIndex == 1){
            focusInitializeAuthTextView(tvDeleteNextFocus);
        } else {
            initializeAuthTextView(tvDeleteCurrentFocus);
            focusInitializeAuthTextView(tvDeleteNextFocus);
            authIndex --;
        }
    }

    //TextView Focus 없는 0 으로 초기화
    private void initializeAuthTextView(TextView targetTextView){
        targetTextView.setText("0");
        targetTextView.setBackground(drawableDelete);
        targetTextView.setTextColor(colorGray);
    }

    //TextView Focus 있는 0 으로 초기화
    private void focusInitializeAuthTextView(TextView targetTextView){
        targetTextView.setText("0");
        targetTextView.setBackground(drawableFocus);
        targetTextView.setTextColor(colorGray);
    }

    //전체 인증번호 초기화
    private void initializeAllAuthTextView(){
        focusInitializeAuthTextView(binding.registPhoneAuthCodeActivityTvCode1);
        initializeAuthTextView(binding.registPhoneAuthCodeActivityTvCode2);
        initializeAuthTextView(binding.registPhoneAuthCodeActivityTvCode3);
        initializeAuthTextView(binding.registPhoneAuthCodeActivityTvCode4);

        authIndex = 1;
        inputAuthCodeList = new ArrayList<>();
    }

    public void btnKeypadClick(String inputNumber) {
        Log.d(TAG, "Log : " + TAG + "btnKeypadClick");
        inputKeypad(inputNumber);
    }

    public void btnDeleteClick() {
        Log.d(TAG, "Log : " + TAG + "btnDeleteClick");
        deleteKeypad();
    }

    public void tvResendClick() {
        Log.d(TAG, "Log : " + TAG + "tvResendClick");
        Toast.makeText(context, "인증번호는 0000입니다.", Toast.LENGTH_SHORT).show();
    }
    /**************************************************************
     *  버튼 클릭 이벤트 끝
     **************************************************************/

    private void startTimer()
    {
        timerTask = new TimerTask()
        {
            int timeTick = 180;  // second 단위 60 * 3

            Integer second = 0;
            Integer minute = 0;
            String showTimer ="00 : 00";
            @Override
            public void run()
            {
                timeTick--;

                second = timeTick % 60;
                minute = timeTick / 60;

                showTimer = String.format("%02d", minute) + " : " + String.format("%02d", second);

                if(timeTick == 0){
                    timerTask.cancel();
                }

                binding.registPhoneAuthCodeActivityTvTimer.post(new Runnable() {
                    @Override
                    public void run() {
                        binding.registPhoneAuthCodeActivityTvTimer.setText(showTimer);
                    }
                });
            }
        };
        timer.schedule(timerTask,0 ,1000);
    }
}
