package com.kj.random_chatting.userRegist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kj.random_chatting.R;
import com.kj.random_chatting.databinding.ActivityMainBinding;
import com.kj.random_chatting.userList.UserListActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class UserRegistActivity extends AppCompatActivity {
    private Button btnSave, btnUploadList, btnGenderMan, btnGenderWoman;
    private Spinner spinnerAge;

    private ActivityMainBinding binding;

    UserRegistService userRegistService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * 하단 네비게이션 기본 설정 시작
         */

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        /**
         * 하단 네비게이션 기본 설정 끝
         */

        initializeView();
        setListener();
    }

    private void initializeView() {
        btnSave = (Button) findViewById(R.id.activity_main_btn_save);
        btnUploadList = (Button) findViewById(R.id.activity_main_btn_upload_list);
        //spinner
        spinnerAge = (Spinner) findViewById(R.id.activity_main_spn_age);
        spinnerAge.setSelection(0);
        ArrayAdapter adapterAge = ArrayAdapter.createFromResource(this,
                R.array.age, android.R.layout.simple_spinner_item);
        adapterAge.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAge.setPrompt("나이를 선택하세요.");
        spinnerAge.setAdapter(adapterAge);

        btnGenderMan = findViewById(R.id.activity_main_btn_gender_man);
        btnGenderWoman = findViewById(R.id.activity_main_btn_gender_woman);
        userRegistService = new UserRegistService(this);
    }

    private void setListener() {
        View.OnClickListener Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.activity_main_btn_gender_man:
                    case R.id.activity_main_btn_gender_woman:
                        String genderText = ((AppCompatButton) v).getText().toString();
                        userRegistService.btnGenderClick(genderText);
                        v.setSelected(!v.isSelected());
                        break;
                    case R.id.activity_main_btn_save:
                        userRegistService.btnSaveClick();
                        break;
                    case R.id.activity_main_btn_upload_list:
                        userRegistService.btnUploadClick();
                        break;
                }
            }
        };

        btnGenderMan.setOnClickListener(Listener);
        btnGenderWoman.setOnClickListener(Listener);
        btnSave.setOnClickListener(Listener);
        btnUploadList.setOnClickListener(Listener);
    }

}