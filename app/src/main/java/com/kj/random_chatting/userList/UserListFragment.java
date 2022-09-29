package com.kj.random_chatting.userList;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.kj.random_chatting.R;
import com.kj.random_chatting.databinding.UserListActivityBinding;
import com.kj.random_chatting.databinding.UserRegistActivityBinding;

import java.util.List;

public class UserListFragment extends Fragment {
    private UserListActivityBinding binding;
    private static final String TAG = "UserListFragment";
    private UserListService userListService;
    private Context context;
    private FragmentActivity fragmentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "Log : " + TAG + " -> onCreateView");
        binding = UserListActivityBinding.inflate(inflater, container, false);
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

    public void initializeView() {
        Log.d(TAG, "Log : " + TAG + " -> initializeView");
        context = getContext();
        fragmentActivity = getActivity();

        userListService = new UserListService(context, binding);
        FindUserInformationTaskRxJava findUserInformationTaskRxJava = new FindUserInformationTaskRxJava(context, binding);
        findUserInformationTaskRxJava.runFunc();
    }


    private void setListener() {
        Log.d(TAG, "Log : " + TAG + " -> setListener");
        View.OnClickListener Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.user_list_activity_btn_next_user:
                        userListService.btnNextUserClick();
                        break;
                }
            }
        };

        binding.userListActivityBtnNextUser.setOnClickListener(Listener);
    }
}
