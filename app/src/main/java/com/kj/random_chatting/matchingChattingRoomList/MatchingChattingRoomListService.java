package com.kj.random_chatting.matchingChattingRoomList;

import static com.kj.random_chatting.common.Constants.EMPTY_IMAGE_PATH;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.kj.random_chatting.common.Enum;
import com.kj.random_chatting.common.SignUpRegistDTO;
import com.kj.random_chatting.databinding.MatchingChattingRoomListFragmentBinding;
import com.kj.random_chatting.databinding.UserListActivityBinding;
import com.kj.random_chatting.registInputEmailPw.RegistInputEmailPwService;
import com.kj.random_chatting.userList.FindUserInformationTaskRxJava;
import com.kj.random_chatting.util.PreferenceUtil;
import com.kj.random_chatting.util.UtilClass;

import java.util.ArrayList;
import java.util.List;

public class MatchingChattingRoomListService {
    private static final String TAG = "MatchingChattingRoomListService";
    private Context context;
    private MatchingChattingRoomListFragmentBinding binding;

    public MatchingChattingRoomListService(Context mContext, MatchingChattingRoomListFragmentBinding mBinding) {
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        context = mContext;
        binding = mBinding;
        initializeView();
    }

    private void initializeView() {
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        PreferenceUtil.init(context);

        MatchingChattingRoomListDTO.matchingChatting temp = new MatchingChattingRoomListDTO.matchingChatting();
        List<MatchingChattingRoomListDTO.matchingChatting> matchingChattingList = new ArrayList<>();

        //temp 1
        temp.setRoomId("1123123133");
        temp.setPhotoName("https://firebasestorage.googleapis.com/v0/b/random-chatting-b52bc.appspot.com/o/photos%2F20230608_170223.png?alt=media&token=d5f60bca-40a8-4ebc-9e83-75ad7c8a9de3");
        temp.setNickName("닉넴2");
        temp.setBirthday("19910221");
        matchingChattingList.add(temp);

        //temp 2
        temp = new MatchingChattingRoomListDTO.matchingChatting();
        temp.setRoomId("33223324");
        temp.setPhotoName("https://firebasestorage.googleapis.com/v0/b/random-chatting-b52bc.appspot.com/o/photos%2F20230608_170917.png?alt=media&token=7273d39d-4485-424d-ab8d-d51a45a09a7f");
        temp.setNickName("민트초코");
        temp.setBirthday("20001010");
        matchingChattingList.add(temp);

        makeList(matchingChattingList);
    }

    private void makeList(List<MatchingChattingRoomListDTO.matchingChatting> matchingChattingList){


    }


    /**************************************************************
     *  버튼 클릭 이벤트 시작
     **************************************************************/

    public void btnActionClick(Enum.ActionStatus actionStatus) {
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());


    }
    /**************************************************************
     *  버튼 클릭 이벤트 끝
     **************************************************************/

}
