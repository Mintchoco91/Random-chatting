package com.kj.random_chatting.UserChattingRoomList;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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
    private FragmentUserChattingRoomListBinding fragmentUserChattingRoomListBinding;
    private Context userChattingListTaskRxJavaContext;
    private ChatListRecyclerAdapter chatListRecyclerAdapter;

    Disposable backgroundTask;

    UtilClass utilClass;

    List<UserChattingRoomListDTO.outputDTO> localChattingRoomList = new ArrayList<>();

    public UserChattingRoomListTaskRxJava(Context context, FragmentUserChattingRoomListBinding binding,ChatListRecyclerAdapter adapter) {
        Log.d(TAG, "Log : " + TAG + " -> UserChattingListTaskRxJava");
        userChattingListTaskRxJavaContext = context;
        fragmentUserChattingRoomListBinding = binding;
        chatListRecyclerAdapter = adapter;
    }

    //결과 처리
    private void resultPost(Integer code) {
        if (code == 0) {
            //전역 변수
            UserChattingRoomListService.mainUserChattingRoomList = localChattingRoomList;

            utilClass = new UtilClass();
            //recyclerView list setting
            utilClass.chatRecyclerViewCreateList(fragmentUserChattingRoomListBinding, UserChattingRoomListService.mainUserChattingRoomList, chatListRecyclerAdapter);

            Toast.makeText(userChattingListTaskRxJavaContext, "리스트 새로고침", Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(userChattingListTaskRxJavaContext, "조회 실패", Toast.LENGTH_LONG).show();
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


}
