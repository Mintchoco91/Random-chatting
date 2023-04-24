package com.kj.random_chatting.registPhoneTextCode;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.kj.random_chatting.databinding.RegistPhoneAuthCodeActivityBinding;
import com.kj.random_chatting.util.UtilClass;

import java.util.Timer;
import java.util.TimerTask;

public class RegistPhoneAuthCodeService extends Activity {
    private static final String TAG = "RegistPhoneAuthCodeService";
    private RegistPhoneAuthCodeActivityBinding binding;
    private Context context;
    private UtilClass utilClass;
    private Integer authIndex;
    TimerTask timerTask;
    Timer timer = new Timer();

    public RegistPhoneAuthCodeService(Context mContext, RegistPhoneAuthCodeActivityBinding mBinding) {
        Log.d(TAG, "Log : " + TAG + " -> RegistPhoneAuthCodeService");
        authIndex = 1;
        context = mContext;
        binding = mBinding;

        startTimer();
        //binding.registPhoneAuthCodeActivityTvCode1.setBackground(@drawa);
    }

    /**************************************************************
     *  버튼 클릭 이벤트 시작
     **************************************************************/

    public void btnKeypadClick(String number) {
        Log.d(TAG, "Log : " + TAG + "btnKeypadClick");
        binding.registPhoneAuthCodeActivityTvCode1.setText(number);
    }

    public void btndeleteClick() {
        Log.d(TAG, "Log : " + TAG + "btndeleteClick");

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
