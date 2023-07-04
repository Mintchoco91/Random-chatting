package com.kj.random_chatting.matchingChattingRoomList;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.kj.random_chatting.R;
import com.kj.random_chatting.common.Enum;
import com.kj.random_chatting.databinding.MatchingChattingRoomListFragmentBinding;
import com.kj.random_chatting.databinding.UserListActivityBinding;
import com.kj.random_chatting.util.UtilClass;

public class MatchingChattingRoomListFragment extends Fragment {
    private static final String TAG = "MatchingChattingRoomListFragment";
    private Context context;
    private MatchingChattingRoomListFragmentBinding binding;
    private MatchingChattingRoomListService matchingChattingRoomListService;
    private FragmentActivity fragmentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        binding = MatchingChattingRoomListFragmentBinding.inflate(inflater, container, false);
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
        matchingChattingRoomListService = new MatchingChattingRoomListService(context, binding);

    }


    private void setListener() {
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        View.OnClickListener Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {

                }
            }
        };

        /*
        binding.userListActivityBtnLike.setOnClickListener(Listener);
        binding.userListActivityBtnDislike.setOnClickListener(Listener);
        binding.userListActivityBtnSuperLiker.setOnClickListener(Listener);

         */
    }
}
