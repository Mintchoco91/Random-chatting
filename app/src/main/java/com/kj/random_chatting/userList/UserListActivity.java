package com.kj.random_chatting.userList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kj.random_chatting.R;

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends Activity {
    public static List<UserListDTO.outputDTO> mainUserList = new ArrayList<>();
    private Button btnNextUser;

    //page (1페이지 부터 시작)
    private Integer currentPageCnt = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_list_activity);
        btnNextUser = (Button) findViewById(R.id.user_list_activity_btn_next_user);

        FindUserInformationTaskRxJava findUserInformationTaskRxJava = new FindUserInformationTaskRxJava(this);
        findUserInformationTaskRxJava.runFunc();

        UserListService userListService = new UserListService(this);

        btnNextUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPageCnt < mainUserList.size()) {
                    userListService.showInformation(mainUserList.get(currentPageCnt));
                    currentPageCnt++;
                }else{
                    Toast.makeText(getApplicationContext(), "더 이상 회원이 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
