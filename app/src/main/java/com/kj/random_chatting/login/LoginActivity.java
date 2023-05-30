package com.kj.random_chatting.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kj.random_chatting.R;
import com.kj.random_chatting.common.ForecdTerminationService;
import com.kj.random_chatting.common.MainActivity;
import com.kj.random_chatting.common.SignUpRegistDTO;
import com.kj.random_chatting.common.SplashActivity;
import com.kj.random_chatting.databinding.LoginActivityBinding;
import com.kj.random_chatting.databinding.RegistInputEmailPwActivityBinding;
import com.kj.random_chatting.registInputEmailPw.RegistInputEmailPwService;
import com.kj.random_chatting.registPhoneNumber.RegistPhoneNumberActivity;
import com.kj.random_chatting.userList.UserListActivity;
import com.kj.random_chatting.userRegist.UserRegistActivity;
import com.kj.random_chatting.util.Retrofit_client;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity {

    private static final String TAG = "LoginActivity";
    private LoginActivityBinding binding;
    private Context context;
    private LoginService loginService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        startService(new Intent(this, ForecdTerminationService.class));
        binding = LoginActivityBinding.inflate(getLayoutInflater());
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
        //필요시 주석 풀것. 데이터는 나옴.
        //Intent intent = getIntent();
        //SignUpRegistDTO.input intentData = (SignUpRegistDTO.input) intent.getSerializableExtra("intentData");

        loginService = new LoginService(context, binding);
    }

    private void setListener() {
        Log.d(TAG, "Log : " + TAG + " -> setListener");
        View.OnClickListener Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.login_activity_btn_regist:
                        loginService.btnRegistClick();
                        break;
                    case R.id.login_activity_btn_find_information:
                        loginService.btnFindInformationClick();
                        break;
                    case R.id.login_activity_btn_login:
                        doLogin();
                        break;
                }
            }
        };

        binding.loginActivityBtnRegist.setOnClickListener(Listener);
        binding.loginActivityBtnFindInformation.setOnClickListener(Listener);
        binding.loginActivityBtnLogin.setOnClickListener(Listener);
    }

    public void doLogin() {
        String email = binding.loginActivityEtEmail.getText().toString().trim();
        String password = binding.loginActivityEtPassword.getText().toString().trim();

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);

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
                        editor.putString("userId", result.getUserId());
                        editor.putString("userName", result.getUserName());
                        editor.commit();

                        Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Log : " + TAG + " -> doLogin/ onFailure_1 : 조회 결과 없음");
                    }
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Log : " + TAG + " -> doLogin/ onFailure_2 : " + t.getMessage());
            }
        });
    }

}
