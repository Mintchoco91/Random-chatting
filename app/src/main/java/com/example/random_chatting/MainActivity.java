package com.example.random_chatting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.random_chatting.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private String jsonString, strUserName, strGender, strAge, strPhoneNumber;
    private EditText etUserName, etPhoneNumber;
    private Button btnSave, btnList, btnUploadList, slideSample;

    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /**
         * Default Setting Start
         */
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        /**
         * Default Setting End
         */

        btnSave = (Button) findViewById(R.id.activity_main_btn_save);
        btnList = (Button) findViewById(R.id.activity_main_btn_list);
        btnUploadList = (Button) findViewById(R.id.activity_main_btn_upload_list);
        slideSample = (Button) findViewById(R.id.activity_main_slide_sample);

        etUserName = (EditText) findViewById(R.id.activity_main_et_user_name);
        etPhoneNumber = (EditText)findViewById(R.id.activity_main_et_phone_number);

        //spinner
        Spinner spinnerAge = (Spinner)findViewById(R.id.activity_main_spn_age);
        spinnerAge.setSelection(0);
        ArrayAdapter adapterAge = ArrayAdapter.createFromResource(this,
                R.array.age, android.R.layout.simple_spinner_item);
        adapterAge.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAge.setPrompt("나이를 선택하세요.");
        spinnerAge.setAdapter(adapterAge);

        Button btnGenderMan = findViewById(R.id.activity_main_btn_gender_man);
        Button btnGenderWoman = findViewById(R.id.activity_main_btn_gender_woman);

        btnGenderMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strGender = "남";
                v.setSelected(!v.isSelected());
                btnGenderWoman.setSelected(false);
            }
        });

        btnGenderWoman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strGender = "여";
                v.setSelected(!v.isSelected());
                btnGenderMan.setSelected(false);
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strUserName = String.valueOf(etUserName.getText()).trim();
                strAge = spinnerAge.getSelectedItem().toString();
                strPhoneNumber = String.valueOf(etPhoneNumber.getText()).trim();

                if(strUserName.length() == 0){
                    Toast.makeText(MainActivity.this, "이름을 입력하세요.", Toast.LENGTH_SHORT).show();
                    etUserName.requestFocus();
                    return;
                }
                if(strGender == null){
                    Toast.makeText(MainActivity.this, "성별을 선택하세요.", Toast.LENGTH_SHORT).show();
                    btnGenderMan.requestFocus();
                    return;
                }
                if(strAge.equals("선택")){
                    Toast.makeText(MainActivity.this, "나이를 선택하세요.", Toast.LENGTH_SHORT).show();
                    spinnerAge.requestFocus();
                    return;
                }
                if(strPhoneNumber.length() == 0){
                    Toast.makeText(MainActivity.this, "핸드폰 번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                    etPhoneNumber.requestFocus();
                    return;
                }

                //다음 단계로 (이미지 업로드)
                Intent intentUpload = new Intent(MainActivity.this, FileUploadActivity.class);
                intentUpload.putExtra("strUserName",strUserName);
                intentUpload.putExtra("strGender",strGender);
                intentUpload.putExtra("strAge",strAge);
                intentUpload.putExtra("strPhoneNumber",strPhoneNumber);

                startActivity(intentUpload);
            }
        });


        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent -> 외부 activity 선언
                Intent intentList = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intentList);
            }
        });

        btnUploadList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentUploadList = new Intent(MainActivity.this, UserListActivity.class);
                startActivity(intentUploadList);
            }
        });

        slideSample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentUploadList = new Intent(MainActivity.this, SlideSample.class);
                startActivity(intentUploadList);
            }
        });
    }

}