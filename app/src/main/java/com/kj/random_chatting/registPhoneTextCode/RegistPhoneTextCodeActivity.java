package com.kj.random_chatting.registPhoneTextCode;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.kj.random_chatting.R;
import com.kj.random_chatting.databinding.ActivityOnboardingBinding;
import com.kj.random_chatting.databinding.RegistPhoneTextCodeActivityBinding;

import java.util.Timer;
import java.util.TimerTask;

public class RegistPhoneTextCodeActivity extends Activity {
    private static final String TAG = "RegistPhoneNumberActivity";
    private RegistPhoneTextCodeActivityBinding binding;
    private Context context;
    private RegistPhoneTextCodeService registPhoneTextCodeService;
    TimerTask timerTask;
    Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = RegistPhoneTextCodeActivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        initializeView();
        setListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    private void initializeView() {
        Log.d(TAG, "Log : " + TAG + " -> initializeView");
        context = this;
        registPhoneTextCodeService = new RegistPhoneTextCodeService(context, binding);
        startTimer();
    }

    private void setListener() {
        Log.d(TAG, "Log : " + TAG + " -> setListener");
        View.OnClickListener Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.activity_onboarding_tv_login_click_description:
                        registPhoneTextCodeService.btnLoginClick();
                        break;
                    case R.id.activity_onboarding_btn_regist:
                        registPhoneTextCodeService.btnRegistClick();
                        break;
                }
            }
        };

        //binding.activityOnboardingTvLoginClickDescription.setOnClickListener(Listener);
        //binding.activityOnboardingBtnRegist.setOnClickListener(Listener);
    }
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

                binding.registPhoneTextCodeActivityTvTimer.post(new Runnable() {
                    @Override
                    public void run() {
                        binding.registPhoneTextCodeActivityTvTimer.setText(showTimer);
                    }
                });
            }
        };
        timer.schedule(timerTask,0 ,1000);
    }
}
