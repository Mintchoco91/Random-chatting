package com.kj.random_chatting.showMyInformation;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.kj.random_chatting.R;
import com.kj.random_chatting.databinding.ShowMyInfomationFragmentBinding;
import com.kj.random_chatting.databinding.UserRegistActivityBinding;
import com.kj.random_chatting.userRegist.UserRegistService;

/**
 * 나중에 사진 업로드부분 fragment 처리할때 참고할 것
 */

public class ShowMyInformationFragment extends Fragment {
    private ShowMyInfomationFragmentBinding binding;
    private static final String TAG = "ShowMyInformationFragment";

    private ShowMyInformationService showMyInformationService;
    private Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "Log : " + TAG + " -> onCreateView");
        binding = ShowMyInfomationFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.d(TAG, "Log : " + TAG + " -> onViewCreated");
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
        Log.d(TAG, "Log : " + TAG + " -> initializeView");
        context = getContext();

        showMyInformationService = new ShowMyInformationService(context, binding);
    }

    private void setListener() {
        Log.d(TAG, "Log : " + TAG + " -> setListener");
        View.OnClickListener Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.show_my_infomation_fragment_btn_picture:
                        break;
                }
            }
        };

        binding.showMyInfomationFragmentBtnPicture.setOnClickListener(Listener);
    }

}
