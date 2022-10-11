package com.kj.random_chatting.UserChattingRoomList;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.kj.random_chatting.R;
import com.kj.random_chatting.databinding.FragmentUserChattingRoomListBinding;
import com.kj.random_chatting.util.ChatListRecyclerAdapter;
import com.kj.random_chatting.util.RecyclerItem;

import java.util.ArrayList;

public class UserChattingRoomListFragment extends Fragment {
    private static final String TAG = "UserChattingRoomListFragment";
    private FragmentUserChattingRoomListBinding binding;
    private Context context;
    private UserChattingRoomListService userChattingRoomListService;

    //adapter 변수. 외부 class로 이동하면서 adapter 에 지속적으로 연결되야해서 static 선언.
    public static ArrayList<RecyclerItem> staticRoomList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserChattingRoomListBinding.inflate(inflater, container, false);
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

        // 리사이클러뷰에 LinearLayoutManager 지정. (vertical)
        binding.fragmentUserChattingRoomListRecyclerviewList.setLayoutManager(new LinearLayoutManager(context));
        ChatListRecyclerAdapter chatListRecyclerAdapter = new ChatListRecyclerAdapter(staticRoomList);

        chatListRecyclerAdapter.setOnItemClickListener(new ChatListRecyclerAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(View v, int pos)
            {
                // 실행 내용
                Toast.makeText(context, "Position:" + pos , Toast.LENGTH_SHORT).show();
            }
        });


        userChattingRoomListService = new UserChattingRoomListService(context, binding, chatListRecyclerAdapter);
    }

    private void setListener() {
        Log.d(TAG, "Log : " + TAG + " -> setListener");
        View.OnClickListener Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.fragment_user_chatting_room_list_btn_make_room:
                        userChattingRoomListService.btnMakeRoomClick();
                        break;
                }
            }
        };

        binding.fragmentUserChattingRoomListBtnMakeRoom.setOnClickListener(Listener);
    }
}
