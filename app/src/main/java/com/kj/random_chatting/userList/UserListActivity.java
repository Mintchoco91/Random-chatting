package com.kj.random_chatting.userList;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.kj.random_chatting.R;
import com.kj.random_chatting.databinding.MainActivityBinding;
import com.kj.random_chatting.databinding.UserListActivityBinding;

import java.util.List;

public class UserListActivity extends Activity {
    private UserListActivityBinding binding;
    UserListService userListService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = UserListActivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        initializeView();
        setListener();
    }

    private void initializeView() {
        userListService = new UserListService(this, binding);
        FindUserInformationTaskRxJava findUserInformationTaskRxJava = new FindUserInformationTaskRxJava(this, binding);
        findUserInformationTaskRxJava.runFunc();
    }


    private void setListener() {
        View.OnClickListener Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.user_list_activity_btn_next_user:
                        userListService.btnNextUserClick();
                        break;
                }
            }
        };

        binding.userListActivityBtnNextUser.setOnClickListener(Listener);
    }
}
