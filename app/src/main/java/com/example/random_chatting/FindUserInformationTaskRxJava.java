package com.example.random_chatting;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

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
import retrofit2.Callback;
import retrofit2.Response;

/**
 * rxJava 형식으로 동기화 처리
 * 유저 정보 검색
 */
public class FindUserInformationTaskRxJava {
    Context userListActivityContext;
    Disposable backgroundTask;

    List<TaskDTO.findUserInformationOutputDTO> localFindUserInfomationList = new ArrayList<>();

    public FindUserInformationTaskRxJava (Context context) {
        userListActivityContext = context;
    }

    //결과 처리
    private void resultPost(Integer code){
        if(code == 0){
            //전역 변수
            UserListActivity.mainFindUserInfomationList = localFindUserInfomationList;

            UserListService userListService = new UserListService(userListActivityContext);
            userListService.showInformation(localFindUserInfomationList.get(0));

            Toast.makeText(userListActivityContext, "조회 성공", Toast.LENGTH_LONG).show();
        }else{
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
                List<TaskDTO.findUserInformationOutputDTO> findUserInfomationList = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        List<String> fileNameList = new ArrayList<>();

                        JSONObject loopJsonObject = jsonArray.getJSONObject(i);
                        // Pulling items from the array
                        TaskDTO.findUserInformationOutputDTO findUserInformationOutput = new TaskDTO.findUserInformationOutputDTO();
                        String id = loopJsonObject.getString("id");
                        String userName = loopJsonObject.getString("userName");
                        String gender = loopJsonObject.getString("gender");
                        String age = loopJsonObject.getString("age");
                        String phoneNumber = loopJsonObject.getString("phoneNumber");
                        String fileName0 = loopJsonObject.getString("fileName0");
                        String fileName1 = loopJsonObject.getString("fileName1");
                        String fileName2 = loopJsonObject.getString("fileName2");
                        String fileName3 = loopJsonObject.getString("fileName3");
                        String fileName4 = loopJsonObject.getString("fileName4");
                        String fileName5 = loopJsonObject.getString("fileName5");
                        String countIdx = loopJsonObject.getString("countIdx");

                        //decode
                        userName = URLDecoder.decode(userName, "utf-8");
                        gender = URLDecoder.decode(gender, "utf-8");

                        fileNameList.add(fileName0);
                        fileNameList.add(fileName1);
                        fileNameList.add(fileName2);
                        fileNameList.add(fileName3);
                        fileNameList.add(fileName4);
                        fileNameList.add(fileName5);

                        //공백 제거
                        fileNameList.removeAll(Arrays.asList("", null));

                        findUserInformationOutput.setId(id);
                        findUserInformationOutput.setUserName(userName);
                        findUserInformationOutput.setGender(gender);
                        findUserInformationOutput.setAge(age);
                        findUserInformationOutput.setPhoneNumber(phoneNumber);
                        findUserInformationOutput.setFileNameList(fileNameList);
                        findUserInformationOutput.setCountIdx(countIdx);

                        findUserInfomationList.add(findUserInformationOutput);
                    } catch (JSONException e) {
                        // json catch
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();    // encoding catch
                    }
                }

                //random 처리
                Collections.shuffle(findUserInfomationList);

                localFindUserInfomationList = findUserInfomationList;
            } else {
                resultCode = 1;
            }
        } catch (JSONException e) {
            resultCode = 2;
        }
        return resultCode;
    }
}
