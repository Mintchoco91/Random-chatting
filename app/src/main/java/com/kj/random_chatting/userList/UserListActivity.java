package com.kj.random_chatting.userList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import com.kj.random_chatting.R;

import java.util.ArrayList;
import java.util.List;

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

    public void initializeView(){
        btnNextUser = (Button) findViewById(R.id.user_list_activity_btn_next_user);
        userListService = new UserListService(this);
        FindUserInformationTaskRxJava findUserInformationTaskRxJava = new FindUserInformationTaskRxJava(this);
        findUserInformationTaskRxJava.runFunc();
    }


    public void setListener(){
        View.OnClickListener Listener = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                switch(v.getId()){
                    case R.id.user_list_activity_btn_next_user:
                        userListService.btnNextUserClick();
                        break;
                }
            }
        };

        btnNextUser.setOnClickListener(Listener);
    }
}
