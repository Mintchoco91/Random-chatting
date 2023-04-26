package com.kj.random_chatting.userChattingRoomList;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.kj.random_chatting.databinding.FragmentUserChattingRoomListBinding;
import com.kj.random_chatting.util.ChatListRecyclerAdapter;
import com.kj.random_chatting.util.RecyclerItem;
import com.kj.random_chatting.util.Retrofit_client;
import com.kj.random_chatting.util.UtilClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;

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
        Log.d(TAG, "Log : " + TAG + " -> UserChattingListTaskRxJava");
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
        Integer resultCode = 0;
        UserChattingRoomListDTO.inputDTO inputParam =
                UserChattingRoomListDTO.inputDTO.builder().build();

        Call<String> call = Retrofit_client.getApiService().searchChattingRoomList(inputParam);
        //동기화 해야 해서 excute() 처리함.
        String jsonResponse = call.execute().body();

        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            if (jsonObject.optString("status").equals("true")) {
                JSONArray jsonArray = jsonObject.getJSONArray("result");
                List<UserChattingRoomListDTO.outputDTO> searchList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject loopJsonObject = jsonArray.getJSONObject(i);
                        // Pulling items from the array
                        UserChattingRoomListDTO.outputDTO userChattingList = new UserChattingRoomListDTO.outputDTO();
                        String userId = loopJsonObject.getString("userId");
                        String roomId = loopJsonObject.getString("roomId");
                        String roomName = loopJsonObject.getString("roomName");
                        boolean isMovie = loopJsonObject.getString("isMovie").equals("1") ? true : false;
                        boolean isDrive = loopJsonObject.getString("isDrive").equals("1") ? true : false;

                        //decode
                        roomName = URLDecoder.decode(roomName, "utf-8");

                        userChattingList.setUserId(userId);
                        userChattingList.setRoomId(roomId);
                        userChattingList.setRoomName(roomName);
                        userChattingList.setMovie(isMovie);
                        userChattingList.setDrive(isDrive);

                        searchList.add(userChattingList);
                    } catch (JSONException e) {
                        // json catch
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();    // encoding catch
                    }
                }

                localChattingRoomList = searchList;
            } else {
                resultCode = 1;
            }
        } catch (JSONException e) {
            resultCode = 2;
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
