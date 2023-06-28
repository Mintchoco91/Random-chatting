package com.kj.random_chatting.userList;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.widget.Toast;

import com.kj.random_chatting.R;
import com.kj.random_chatting.databinding.UserListActivityBinding;
import com.kj.random_chatting.util.PreferenceUtil;
import com.kj.random_chatting.util.Retrofit_client;
import com.kj.random_chatting.util.UtilClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;

/**
 * rxJava 형식으로 동기화 처리
 * 유저 정보 검색
 */
public class FindUserInformationTaskRxJava {
    private UserListActivityBinding binding;
    Context context;
    Disposable backgroundTask;

    List<UserListDTO.outputDTO> userList = new ArrayList<>();
    private UserListService userListService;

    private UtilClass utilClass;

    public FindUserInformationTaskRxJava(Context mContext, UserListActivityBinding mBinding, List<UserListDTO.outputDTO> mUserList, UserListService mUserListService) {
        context = mContext;
        binding = mBinding;
        userList = mUserList;
        userListService = mUserListService;
        PreferenceUtil.init(context);
    }

    //결과 처리
    private void resultPost(Integer code) {
        if (code == 0) {
            userListService.showInformation(userList.get(0));
        } else {
            Toast.makeText(context, "조회 실패", Toast.LENGTH_SHORT).show();
        }
    }

    // backgroundTask를 실행하는 메소드. ex) main에서 호출 시 : insertLoginTaskRxjava.runFunc(...params)
    public void runFunc(String... params) {
        backgroundTask = Observable.fromCallable(() -> {
                    return findUserInformation();
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> resultPost(result));
    }

    //유저 정보 조회
    private Integer findUserInformation() throws IOException {
        Integer resultCode = 0;
        String userId = PreferenceUtil.getUserId(null);
        UserListDTO.searchUserInputDTO inputDTO = new UserListDTO.searchUserInputDTO();
        inputDTO.setUserId(userId);

        Call<String> call = Retrofit_client.getApiService(context).searchUserList(inputDTO);

        //동기화 해야 해서 excute() 처리함.
        String jsonResponse = call.execute().body();

        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            if (jsonObject.optString("status").equals("true")) {
                JSONArray jsonArray = jsonObject.getJSONArray("result");
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject loopJsonObject = jsonArray.getJSONObject(i);
                        // Pulling items from the array
                        UserListDTO.outputDTO userListOutput = new UserListDTO.outputDTO();
                        String id = loopJsonObject.getString("id");
                        String nickName = loopJsonObject.getString("nickName");
                        String birthday = loopJsonObject.getString("birthday");
                        String gender = loopJsonObject.getString("gender");
                        String photoName = loopJsonObject.getString("photoName");

                        //decode
                        nickName = URLDecoder.decode(nickName, "utf-8");
                        gender = URLDecoder.decode(gender, "utf-8");

                        userListOutput.setId(id);
                        userListOutput.setNickName(nickName);
                        userListOutput.setBirthday(birthday);
                        userListOutput.setGender(gender);
                        userListOutput.setPhotoName(photoName);
                        userList.add(userListOutput);

                    } catch (JSONException e) {
                        // json catch
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();    // encoding catch
                    }
                }

                //데이터가 하나도 없을경우 dummy data
                if(jsonArray.length() == 0){
                    UserListDTO.outputDTO userListOutput = new UserListDTO.outputDTO();
                    userListOutput.setId("99999999");
                    userListOutput.setNickName("홍길동");
                    userListOutput.setBirthday("19910101");
                    userListOutput.setGender("남");
                    userList.add(userListOutput);
                }
                //random 처리
                Collections.shuffle(userList);
            } else {
                resultCode = 1;
            }
        } catch (JSONException e) {
            resultCode = 2;
        }
        return resultCode;
    }
}
