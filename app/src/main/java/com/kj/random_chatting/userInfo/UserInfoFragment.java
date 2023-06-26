package com.kj.random_chatting.userInfo;

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
import com.kj.random_chatting.common.Enum;
import com.kj.random_chatting.databinding.UserRegistActivityBinding;
import com.kj.random_chatting.userRegist.UserRegistService;
import com.kj.random_chatting.util.UtilClass;

/**
 * 나중에 사진 업로드부분 fragment 처리할때 참고할 것
 */

public class UserInfoFragment extends Fragment {
    private UserRegistActivityBinding binding;
    private static final String TAG = "UserInfoFragment";

    private UserRegistService userRegistService;
    private Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        binding = UserRegistActivityBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        super.onViewCreated(view, savedInstanceState);
        initializeView();
        setListener();
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }

    private void initializeView() {
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        context = getContext();

        binding.activityMainSpnAge.setSelection(0);
        ArrayAdapter adapterAge = ArrayAdapter.createFromResource(context,
                R.array.age, android.R.layout.simple_spinner_item);
        adapterAge.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.activityMainSpnAge.setPrompt("나이를 선택하세요.");
        binding.activityMainSpnAge.setAdapter(adapterAge);
        userRegistService = new UserRegistService(context, binding);
    }

    private void setListener() {
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
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
                }
            }
        };

        binding.activityMainBtnGenderMan.setOnClickListener(Listener);
        binding.activityMainBtnGenderWoman.setOnClickListener(Listener);
        binding.activityMainBtnSave.setOnClickListener(Listener);
    }

}
