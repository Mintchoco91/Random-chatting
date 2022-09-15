package com.kj.random_chatting.userList;

import android.content.Context;
import android.widget.Toast;

import com.kj.random_chatting.R;
import com.kj.random_chatting.databinding.UserListActivityBinding;
import com.kj.random_chatting.util.Retrofit_client;

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
    private UserListActivityBinding userListActivityBinding;
    Context userListActivityContext;
    Disposable backgroundTask;

    List<UserListDTO.outputDTO> localUserList = new ArrayList<>();

    public FindUserInformationTaskRxJava(Context context, UserListActivityBinding binding) {
        userListActivityContext = context;
        userListActivityBinding = binding;
    }

    //결과 처리
    private void resultPost(Integer code) {
        if (code == 0) {
            //전역 변수
            UserListService.mainUserList = localUserList;

            UserListService userListService = new UserListService(userListActivityContext, userListActivityBinding);
            userListService.showInformation(localUserList.get(0));

            Toast.makeText(userListActivityContext, "조회 성공", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(userListActivityContext, "조회 실패", Toast.LENGTH_LONG).show();
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
        Call<String> call = Retrofit_client.getApiService().findUserInformation(
                "select");
        //동기화 해야 해서 excute() 처리함.
        String jsonResponse = call.execute().body();

        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            if (jsonObject.optString("status").equals("true")) {
                JSONArray jsonArray = jsonObject.getJSONArray("result");
                List<UserListDTO.outputDTO> userList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        List<String> fileNameList = new ArrayList<>();

                        JSONObject loopJsonObject = jsonArray.getJSONObject(i);
                        // Pulling items from the array
                        UserListDTO.outputDTO userListOutput = new UserListDTO.outputDTO();
                        String id = loopJsonObject.getString("id");
                        String userName = loopJsonObject.getString("userName");
                        String gender = loopJsonObject.getString("gender");
                        String age = loopJsonObject.getString("age");
                        String phoneNumber = loopJsonObject.getString("phoneNumber");

                        String fileName = "";
                        for (int num = 0; num < 6; num++) {
                            fileName = loopJsonObject.getString("fileName" + num);
                            if (fileName.equals("")) {
                                break;
                            } else {
                                fileNameList.add(fileName);
                            }
                        }

                        //decode
                        userName = URLDecoder.decode(userName, "utf-8");
                        gender = URLDecoder.decode(gender, "utf-8");

                        userListOutput.setId(id);
                        userListOutput.setUserName(userName);
                        userListOutput.setGender(gender);
                        userListOutput.setAge(age);
                        userListOutput.setPhoneNumber(phoneNumber);
                        userListOutput.setFileNameList(fileNameList);

                        userList.add(userListOutput);
                    } catch (JSONException e) {
                        // json catch
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();    // encoding catch
                    }
                }

                //random 처리
                Collections.shuffle(userList);

                localUserList = userList;
            } else {
                resultCode = 1;
            }
        } catch (JSONException e) {
            resultCode = 2;
        }
        return resultCode;
    }
}
