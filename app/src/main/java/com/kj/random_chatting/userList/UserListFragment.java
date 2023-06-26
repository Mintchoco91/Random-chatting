package com.kj.random_chatting.userList;

import static android.content.Context.MODE_PRIVATE;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.kj.random_chatting.R;
import com.kj.random_chatting.common.Enum;
import com.kj.random_chatting.databinding.UserListActivityBinding;
import com.kj.random_chatting.util.UtilClass;

import java.util.ArrayList;
import java.util.List;

public class UserListFragment extends Fragment {
    private static final String TAG = "UserListFragment";
    private Context context;
    private UserListActivityBinding binding;
    private UserListService userListService;
    private FragmentActivity fragmentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        binding = UserListActivityBinding.inflate(inflater, container, false);
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

    public void initializeView() {
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        context = getContext();

        fragmentActivity = getActivity();
        userListService = new UserListService(context, binding);

    }


    private void setListener() {
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        View.OnClickListener Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.user_list_activity_btn_dislike:
                        userListService.btnActionClick(Enum.ActionStatus.DISLIKE);
                        break;
                    case R.id.user_list_activity_btn_Like:
                        userListService.btnActionClick(Enum.ActionStatus.LIKE);
                        break;
                    case R.id.user_list_activity_btn_super_liker:
                        userListService.btnActionClick(Enum.ActionStatus.SUPERLIKE);
                        break;
                }
            }
        };

        binding.userListActivityBtnLike.setOnClickListener(Listener);
        binding.userListActivityBtnDislike.setOnClickListener(Listener);
        binding.userListActivityBtnSuperLiker.setOnClickListener(Listener);
    }
}
