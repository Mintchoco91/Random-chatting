package com.kj.random_chatting.userRegist;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.kj.random_chatting.R;

/**
 * 나중에 사진 업로드부분 fragment 처리할때 참고할 것
 */

public class UserRegistFragment extends Fragment {
    private static final String TAG = "UserListFragment";
    private Button btnSave, btnUploadList, btnGenderMan, btnGenderWoman;
    private Spinner spinnerAge;

    private UserRegistService userRegistService;
    private Context context;
    private FragmentActivity fragmentActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "Log : " + TAG + " -> onCreateView");
        return inflater.inflate(R.layout.user_regist_activity, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.d(TAG, "Log : " + TAG + " -> onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        initializeView();
        setListener();
    }

    private void initializeView() {
        Log.d(TAG, "Log : " + TAG + " -> initializeView");
        context = getContext();
        fragmentActivity = getActivity();

        btnSave = (Button) fragmentActivity.findViewById(R.id.activity_main_btn_save);
        btnUploadList = (Button) fragmentActivity.findViewById(R.id.activity_main_btn_upload_list);
        //spinner
        spinnerAge = (Spinner) fragmentActivity.findViewById(R.id.activity_main_spn_age);
        spinnerAge.setSelection(0);
        ArrayAdapter adapterAge = ArrayAdapter.createFromResource(context,
                R.array.age, android.R.layout.simple_spinner_item);
        adapterAge.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAge.setPrompt("나이를 선택하세요.");
        spinnerAge.setAdapter(adapterAge);

        btnGenderMan = fragmentActivity.findViewById(R.id.activity_main_btn_gender_man);
        btnGenderWoman = fragmentActivity.findViewById(R.id.activity_main_btn_gender_woman);
        userRegistService = new UserRegistService(context);
    }

    private void setListener() {
        Log.d(TAG, "Log : " + TAG + " -> setListener");
        View.OnClickListener Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.activity_main_btn_gender_man:
                    case R.id.activity_main_btn_gender_woman:
                        String genderText = ((Button) v).getText().toString();
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
