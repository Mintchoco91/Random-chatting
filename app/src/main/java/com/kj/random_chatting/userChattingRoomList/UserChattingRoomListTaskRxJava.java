package com.kj.random_chatting.userChattingRoomList;

import android.content.Context;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.kj.random_chatting.common.Enum;
import com.kj.random_chatting.databinding.FragmentUserChattingRoomListBinding;
import com.kj.random_chatting.util.ChatListRecyclerAdapter;
import com.kj.random_chatting.util.RecyclerItem;
import com.kj.random_chatting.util.Retrofit_client;
import com.kj.random_chatting.util.UtilClass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

/**
 * rxJava 형식으로 동기화 처리
 * 채팅방 조회
 */

public class UserChattingRoomListTaskRxJava {
    private static final String TAG = "UserChattingListTaskRxJava";
    private FragmentUserChattingRoomListBinding binding;
    private Context context;

    Disposable backgroundTask;

    List<UserChattingRoomListDTO.outputDTO> localChattingRoomList = new ArrayList<>();

    public UserChattingRoomListTaskRxJava(Context mContext, FragmentUserChattingRoomListBinding mBinding) {
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        context = mContext;
        binding = mBinding;
    }

    //결과 처리
    private void resultPost(Integer code) {
        if (code == 0) {
            //전역 변수
            UserChattingRoomListService.mainUserChattingRoomList = localChattingRoomList;

            //recyclerView list setting
            ArrayList<RecyclerItem> recyclerRoomList = new ArrayList<>();
            recyclerRoomList = chatRecyclerViewCreateList(context, binding, UserChattingRoomListService.mainUserChattingRoomList);
            recyclerInit(recyclerRoomList);
        }else{
            Toast.makeText(context, "조회 실패", Toast.LENGTH_SHORT).show();
        }
    }

    // backgroundTask를 실행하는 메소드.
    public void searchChattingRoomRunFunc(String... params) {
        backgroundTask = Observable.fromCallable(() -> {
                    return searchChattingRoomList();
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> resultPost(result));
    }

    //유저 정보 조회
    private Integer searchChattingRoomList() throws IOException {
        Integer resultCode;
        UserChattingRoomListDTO.inputDTO inputParam =
                UserChattingRoomListDTO.inputDTO.builder().build();

        try {
            Response<List<UserChattingRoomListDTO.outputDTO>> response = Retrofit_client.getApiService(context).searchChattingRoomList(inputParam).execute();
            if (response.isSuccessful()) {
                // select 성공
                resultCode = 0;
                localChattingRoomList = response.body();
            } else {
                // select 실패
                resultCode = 1;
                UtilClass.writeLog(TAG, response.errorBody().string(), Enum.LogType.E);
            }
        } catch (IOException e) {
            // 네트워크 연결 오류
            resultCode = 2;
            UtilClass.writeLog(TAG, "Network Connection Error!", Enum.LogType.E);
        } catch (Exception e) {
            // 그 외 오류
            resultCode = 3;
            UtilClass.writeLog(TAG, e.toString(), Enum.LogType.E);
        }

        return resultCode;
    }


    //ChatList부분 RecycleViewList 구조 생성해주는 함수
    private ArrayList<RecyclerItem> chatRecyclerViewCreateList(Context mContext, FragmentUserChattingRoomListBinding mBinding, List<UserChattingRoomListDTO.outputDTO> roomList){
        ArrayList<RecyclerItem> resultList  = new ArrayList<>();
        for(UserChattingRoomListDTO.outputDTO target : roomList){
            chatRecyclerViewAddItem(resultList, target.getRoomId(), target.getRoomName());
        }
        return resultList;
    }

    private void chatRecyclerViewAddItem(ArrayList<RecyclerItem> recyclerRoomList, String roomId, String roomName) {
        RecyclerItem item = new RecyclerItem();

        item.setRoomId(roomId);
        item.setRoomName(roomName);

        recyclerRoomList.add(item);
    }

    private void recyclerInit(ArrayList<RecyclerItem> recyclerRoomList){
        //recycler 객체 생성
        ChatListRecyclerAdapter adapter = new ChatListRecyclerAdapter(context, recyclerRoomList) ;
        binding.fragmentUserChattingRoomListRecyclerviewList.setAdapter(adapter) ;
        // 리사이클러뷰에 LinearLayoutManager 지정. (vertical)
        binding.fragmentUserChattingRoomListRecyclerviewList.setLayoutManager(new LinearLayoutManager(context));
        adapter.notifyDataSetChanged() ;
        Toast.makeText(context, "리스트 새로고침 완료", Toast.LENGTH_SHORT).show();
    }

}
