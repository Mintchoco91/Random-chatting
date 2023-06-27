package com.kj.random_chatting.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.kj.random_chatting.R;
import com.kj.random_chatting.common.Enum;
import com.kj.random_chatting.common.ForecdTerminationService;
import com.kj.random_chatting.common.MainActivity;
import com.kj.random_chatting.databinding.LoginActivityBinding;
import com.kj.random_chatting.util.Retrofit_client;
import com.kj.random_chatting.util.UtilClass;

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
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
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
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    private void initializeView() {
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        context = this;
        //필요시 주석 풀것. 데이터는 나옴.
        //Intent intent = getIntent();
        //SignUpRegistDTO.input intentData = (SignUpRegistDTO.input) intent.getSerializableExtra("intentData");

        loginService = new LoginService(context, binding);
    }

    private void setListener() {
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
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

        LoginDTO.input loginRequest = new LoginDTO.input();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);


        Retrofit_client.getApiService().getLoginResponse(loginRequest).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String jsonResponse = response.body();
                    try {
                        JSONObject jsonObject = new JSONObject(jsonResponse);

                        LoginDTO.output output = new LoginDTO.output();
                        output.setStatus(jsonObject.optString("status"));

                        if (output.getStatus().equals("true")) {
                            // 토큰을 저장한다.
                            SharedPreferences prefs = getSharedPreferences("token_prefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();

                            output.setAccessToken(jsonObject.optString("access_token"));
                            output.setRefreshToken(jsonObject.optString("refresh_token"));
                            output.setId(jsonObject.optString("id"));
                            output.setCountryCode(jsonObject.optString("countryCode"));
                            output.setPhoneNumber(jsonObject.optString("phoneNumber"));
                            output.setNickName(jsonObject.optString("nickName"));
                            output.setBirthday(jsonObject.optString("birthday"));
                            output.setGender(jsonObject.optString("gender"));
                            output.setEmail(jsonObject.optString("email"));
                            output.setPassword(jsonObject.optString("password"));

                            editor.putString("access_token", output.getAccessToken());
                            editor.putString("refresh_token", output.getRefreshToken());
                            editor.putString("userId", output.getId());
                            editor.putString("countryCode", output.getCountryCode());
                            editor.putString("phoneNumber", output.getPhoneNumber());
                            editor.putString("nickName", output.getNickName());
                            editor.putString("birthday", output.getBirthday());
                            editor.putString("gender", output.getGender());
                            editor.putString("email", output.getEmail());
                            editor.putString("password", output.getPassword());

                            editor.commit();

                            Toast.makeText(context, "로그인 성공", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(context, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(context, "로그인 실패", Toast.LENGTH_SHORT).show();
                            UtilClass.writeLog(TAG, "Log : " + TAG + " -> doLogin/ onFailure_1 : 조회 결과 없음", Enum.LogType.E);
                        }
                    }catch(Exception e){
                        Toast.makeText(context, "로그인 실패", Toast.LENGTH_SHORT).show();
                        UtilClass.writeLog(TAG, "Log : " + TAG + " -> doLogin/ onFailure_2 : " + e.getMessage(), Enum.LogType.E);
                    }

                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, "로그인 실패", Toast.LENGTH_SHORT).show();
                UtilClass.writeLog(TAG, "Log : " + TAG + " -> doLogin/ onFailure_3 : " + t.getMessage(), Enum.LogType.E);
            }
        });
    }

}
