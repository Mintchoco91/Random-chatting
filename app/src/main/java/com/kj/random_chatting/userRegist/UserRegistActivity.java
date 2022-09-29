package com.kj.random_chatting.userRegist;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.kj.random_chatting.R;
import com.kj.random_chatting.databinding.MainActivityBinding;
import com.kj.random_chatting.databinding.UserRegistActivityBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatRadioButton;

public class UserRegistActivity extends AppCompatActivity {
    private UserRegistActivityBinding binding;

    UserRegistService userRegistService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = UserRegistActivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        initializeView();
        setListener();
    }

    private void initializeView() {
        binding.activityMainSpnAge.setSelection(0);
        ArrayAdapter adapterAge = ArrayAdapter.createFromResource(this,
                R.array.age, android.R.layout.simple_spinner_item);
        adapterAge.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.activityMainSpnAge.setPrompt("나이를 선택하세요.");
        binding.activityMainSpnAge.setAdapter(adapterAge);
        userRegistService = new UserRegistService(this, binding);
    }

    private void setListener() {
        View.OnClickListener Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.activity_main_btn_gender_man:
                    case R.id.activity_main_btn_gender_woman:
                        String genderText = ((AppCompatRadioButton) v).getText().toString();
                        userRegistService.btnGenderClick(genderText);
                        v.setSelected(!v.isSelected());
                        break;
                    case R.id.activity_main_btn_save:
                        userRegistService.btnSaveClick();
                        break;
                }
            }
        };

        binding.activityMainBtnGenderMan.setOnClickListener(Listener);
        binding.activityMainBtnGenderWoman.setOnClickListener(Listener);
        binding.activityMainBtnSave.setOnClickListener(Listener);
    }

}