package com.kj.random_chatting.userChatting;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kj.random_chatting.R;
import com.kj.random_chatting.databinding.FragmentUserChattingBinding;
import com.kj.random_chatting.databinding.UserRegistActivityBinding;
import com.kj.random_chatting.userRegist.UserRegistService;

public class UserChattingFragment extends Fragment {
    private FragmentUserChattingBinding binding;
    private static final String TAG = "UserChattingFragment";
    private Context context;

    private UserChattingService userChattingService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserChattingBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.d(TAG, "Log : " + TAG + " -> onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        initializeView();
        setListener();
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }


    private void initializeView() {
        Log.d(TAG, "Log : " + TAG + " -> initializeView");
        context = getContext();

        userChattingService = new UserChattingService(context, binding);
    }

    private void setListener() {
        Log.d(TAG, "Log : " + TAG + " -> setListener");
        View.OnClickListener Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.fragment_user_chatting_btn_send:
                        String chatMessage = binding.fragmentUserChattingEtMessage.getText().toString();
                        userChattingService.btnSendClick(chatMessage);
                        break;
                }
            }
        };

        binding.fragmentUserChattingBtnSend.setOnClickListener(Listener);
    }
}