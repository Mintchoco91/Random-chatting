package com.kj.random_chatting.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.kj.random_chatting.R;
import com.kj.random_chatting.userList.UserListActivity;
import com.kj.random_chatting.userRegist.UserRegistActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText user_id, user_password;
    private Button login_button, regist_button, list_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_login);

        // 회원가입 버튼 관련 리스너 등록
        regist_button = findViewById( R.id.button_regist );
        regist_button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( LoginActivity.this, UserRegistActivity.class );
                startActivity( intent );
            }
        });

        // 회원 목록 조회
        list_button = findViewById(R.id.button_user_list);
        list_button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( LoginActivity.this, UserListActivity.class );
                startActivity( intent );
            }
        });

    }

}
