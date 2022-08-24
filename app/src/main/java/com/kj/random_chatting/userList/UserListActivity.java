package com.kj.random_chatting.userList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.kj.random_chatting.R;

public class UserListActivity extends Activity {
    private Button btnNextUser;

    UserListService userListService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_list_activity);

        initializeView();
        setListener();
    }

    private void initializeView() {
        btnNextUser = (Button) findViewById(R.id.user_list_activity_btn_next_user);
        userListService = new UserListService(this);
        FindUserInformationTaskRxJava findUserInformationTaskRxJava = new FindUserInformationTaskRxJava(this);
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

        btnNextUser.setOnClickListener(Listener);
    }
}
