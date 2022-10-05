package com.kj.random_chatting.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kj.random_chatting.R;
import com.kj.random_chatting.common.MainActivity;
import com.kj.random_chatting.common.SplashActivity;
import com.kj.random_chatting.databinding.LoginActivityBinding;
import com.kj.random_chatting.userList.UserListActivity;
import com.kj.random_chatting.userRegist.UserRegistActivity;
import com.kj.random_chatting.util.Retrofit_client;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private LoginActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LoginActivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // 회원가입 버튼 관련 리스너 등록
        binding.loginActivityBtnRegist.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( LoginActivity.this, UserRegistActivity.class );
                startActivity( intent );
            }
        });

        // 회원 목록 조회
        binding.buttonUserList.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( LoginActivity.this, MainActivity.class );
                startActivity( intent );
            }
        });

        // 로그인 버튼 관련
        binding.loginActivityBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doLogin();
            }
        });
    }

    public void doLogin() {
        String email = binding.loginActivityEtEmail.getText().toString().trim();
        String password = binding.loginActivityEtPassword.getText().toString().trim();

        LoginRequest loginRequest = new LoginRequest(email, password);

        Retrofit_client.getApiService().getLoginResponse(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse result = response.body();
                    int resultCode = result.getCode();

                    if (resultCode == 200) {
                        // 토큰을 저장한다.
                        SharedPreferences prefs = getSharedPreferences("token_prefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        String token = result.getToken();
                        editor.putString("token", token);
                        editor.commit();

                        Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(LoginActivity.this, UserListActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_LONG).show();
                    }


                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });

    }

}
