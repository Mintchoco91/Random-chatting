package com.kj.random_chatting.userRegist;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.kj.random_chatting.R;
import com.kj.random_chatting.databinding.ActivityMainBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class UserRegistActivity extends AppCompatActivity {
    private Button btnSave, btnUploadList, btnGenderMan, btnGenderWoman;
    private Spinner spinnerAge;

    private ActivityMainBinding binding;

    UserRegistService userRegistService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_regist_activity);

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