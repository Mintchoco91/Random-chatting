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

import java.util.List;

public class UserListFragment extends Fragment {
    private static final String TAG = "UserListFragment";
    private Button btnNextUser;
    private UserListService userListService;
    private Context context;
    private FragmentActivity fragmentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "Log : " + TAG + " -> onCreateView");
        return inflater.inflate(R.layout.user_list_activity, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.d(TAG, "Log : " + TAG + " -> onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        initializeView();
        setListener();
    }

    public void initializeView() {
        Log.d(TAG, "Log : " + TAG + " -> initializeView");
        context = getContext();
        fragmentActivity = getActivity();

        btnNextUser = (Button) fragmentActivity.findViewById(R.id.user_list_activity_btn_next_user);
        userListService = new UserListService(context);
        FindUserInformationTaskRxJava findUserInformationTaskRxJava = new FindUserInformationTaskRxJava(context);
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

        btnNextUser.setOnClickListener(Listener);
    }
}
