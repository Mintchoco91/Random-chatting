package com.kj.random_chatting.UserChattingRoomList;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.kj.random_chatting.R;
import com.kj.random_chatting.databinding.FragmentUserChattingRoomListBinding;
import com.kj.random_chatting.util.RecyclerImageTextAdapter;
import com.kj.random_chatting.util.RecyclerItem;

import java.util.ArrayList;

public class UserChattingRoomListFragment extends Fragment {
    private static final String TAG = "UserChattingRoomListFragment";
    private FragmentUserChattingRoomListBinding binding;
    private Context context;
    private UserChattingRoomListService userChattingRoomListService;

    RecyclerImageTextAdapter mAdapter = null ;
    ArrayList<RecyclerItem> mList = new ArrayList<RecyclerItem>();

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

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        mAdapter = new RecyclerImageTextAdapter(mList) ;
        binding.fragmentUserChattingRoomListRecyclerviewList.setAdapter(mAdapter) ;
        // 리사이클러뷰에 LinearLayoutManager 지정. (vertical)
        binding.fragmentUserChattingRoomListRecyclerviewList.setLayoutManager(new LinearLayoutManager(context));
        userChattingRoomListService = new UserChattingRoomListService(context, binding);


        // 아이템 추가.
        addItem("Box", "Account Box Black 36dp") ;
        // 두 번째 아이템 추가.
        addItem("Circle", "Account Circle Black 36dp") ;
        // 세 번째 아이템 추가.
        addItem("Ind", "Assignment Ind Black 36dp") ;

        mAdapter.notifyDataSetChanged() ;
    }

    //kw sample make
    public void addItem(String title, String desc) {
        RecyclerItem item = new RecyclerItem();

        item.setTitle(title);
        item.setDesc(desc);

        mList.add(item);
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
