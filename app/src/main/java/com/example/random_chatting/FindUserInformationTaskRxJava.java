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
    private TextView tvName, tvGender, tvAge, tvPhoneNumber;
    static Context userListActivityContext;
    Disposable backgroundTask;

    //slider
    private ViewPager2 sliderViewPager;
    private LinearLayout layoutIndicator;

    List<TaskDTO.findUserInformationOutputDTO> localFindUserInfomationList = new ArrayList<>();

    public FindUserInformationTaskRxJava (Context context) {
        userListActivityContext = context;
    }

    //결과 처리
    private void resultPost(Integer code){
        if(code == 0){
            showInformation(localFindUserInfomationList.get(0));
            UserListActivity.mainFindUserInfomationList = localFindUserInfomationList;

            Toast.makeText(userListActivityContext, "조회 성공", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(userListActivityContext, "조회 실패", Toast.LENGTH_LONG).show();
        }
    }

    // backgroundTask를 실행하는 메소드. ex) main에서 호출 시 : insertLoginTaskRxjava.runFunc(...params)
    public void runFunc(String... params) {
        //onPreExecute(task 시작 전 실행될 코드 여기에 작성)
        backgroundTask = Observable.fromCallable(() -> {
                    return findUserInformation();
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> resultPost(result));
    }

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

    public void showInformation(TaskDTO.findUserInformationOutputDTO info) {
        tvName = ((TextView) ((Activity) userListActivityContext).findViewById(R.id.user_list_activity_tv_name));
        tvGender = ((TextView) ((Activity) userListActivityContext).findViewById(R.id.user_list_activity_tv_gender));
        tvAge = ((TextView) ((Activity) userListActivityContext).findViewById(R.id.user_list_activity_tv_age));
        tvPhoneNumber = ((TextView) ((Activity) userListActivityContext).findViewById(R.id.user_list_activity_tv_phone_number));

        tvName.setText(info.getUserName());
        tvGender.setText(info.getGender());
        tvAge.setText(info.getAge());
        tvPhoneNumber.setText(info.getPhoneNumber());

        if (info.getFileNameList().size() > 0) {
            String[] fileNameArray = info.getFileNameList().toArray(new String[info.getFileNameList().size()]);
            //Slide처리
            showPhoto(userListActivityContext, fileNameArray);
        } else {
            Toast.makeText(userListActivityContext, "이미지가 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    //이미지 slide 추가 해야함.
    public void showPhoto(Context context, String[] fileNameArray) {
        sliderViewPager = ((ViewPager2) ((Activity) userListActivityContext).findViewById(R.id.sliderViewPager));
        layoutIndicator = ((LinearLayout) ((Activity) userListActivityContext).findViewById(R.id.layoutIndicators));

        sliderViewPager.setOffscreenPageLimit(1);
        sliderViewPager.setAdapter(new ImageSliderAdapter(context, fileNameArray));

        sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentIndicator(position);
            }
        });

        setupIndicators(fileNameArray.length);
    }

    //slider
    private void setupIndicators(int count) {
        layoutIndicator.removeAllViews();
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(16, 8, 16, 8);

        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(userListActivityContext);
            indicators[i].setImageDrawable(ContextCompat.getDrawable(userListActivityContext,
                    R.drawable.bg_indicator_inactive));
            indicators[i].setLayoutParams(params);
            layoutIndicator.addView(indicators[i]);
        }
        setCurrentIndicator(0);
    }

    private void setCurrentIndicator(int position) {
        int childCount = layoutIndicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) layoutIndicator.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        userListActivityContext,
                        R.drawable.bg_indicator_active
                ));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        userListActivityContext,
                        R.drawable.bg_indicator_inactive
                ));
            }
        }
    }

}
