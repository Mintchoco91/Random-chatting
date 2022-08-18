package com.example.random_chatting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserListActivity extends Activity {
    public static List<TaskDTO.findUserInformationOutputDTO> mainFindUserInfomationList = new ArrayList<>();

    private TextView tvName, tvGender, tvAge, tvPhoneNumber;
    private Button btnNextUser;

    // 이미지 폴더 경로 참조
    private StorageReference storageRef;

    //slider
    private ViewPager2 sliderViewPager;
    private LinearLayout layoutIndicator;

    //page (1페이지 부터 시작)
    private Integer currentPageCnt = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_list_activity);

        tvName = (TextView) findViewById(R.id.user_list_activity_tv_name);
        tvGender = (TextView) findViewById(R.id.user_list_activity_tv_gender);
        tvAge = (TextView) findViewById(R.id.user_list_activity_tv_age);
        tvPhoneNumber = (TextView) findViewById(R.id.user_list_activity_tv_phone_number);
        btnNextUser = (Button) findViewById(R.id.user_list_activity_btn_next_user);
        storageRef = FirebaseStorage.getInstance().getReference();

        FindUserInformationTaskRxJava findUserInformationTaskRxJava = new FindUserInformationTaskRxJava(this);
        findUserInformationTaskRxJava.runFunc();

        /* 전체 불러오기 주석처리(현재 안씀)
        btDirectoryImageLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAllPhoto();
            }
        });
         */


        btnNextUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPageCnt < mainFindUserInfomationList.size()) {
                    showInformation(mainFindUserInfomationList.get(currentPageCnt));
                    currentPageCnt++;
                }else{
                    Toast.makeText(getApplicationContext(), "더 이상 회원이 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //slider
    private void setupIndicators(int count) {
        layoutIndicator.removeAllViews();
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(16, 8, 16, 8);

        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(this);
            indicators[i].setImageDrawable(ContextCompat.getDrawable(this,
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
                        this,
                        R.drawable.bg_indicator_active
                ));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        this,
                        R.drawable.bg_indicator_inactive
                ));
            }
        }
    }

    public void showInformation(TaskDTO.findUserInformationOutputDTO info) {
        tvName.setText(info.getUserName());
        tvGender.setText(info.getGender());
        tvAge.setText(info.getAge());
        tvPhoneNumber.setText(info.getPhoneNumber());

        if (info.getFileNameList().size() > 0) {
            String[] fileNameArray = info.getFileNameList().toArray(new String[info.getFileNameList().size()]);
            //Slide처리
            showPhoto(this, fileNameArray);
        } else {
            Toast.makeText(getApplicationContext(), "이미지가 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    //이미지 slide 추가 해야함.
    public void showPhoto(Context context, String[] fileNameArray) {
        sliderViewPager = findViewById(R.id.sliderViewPager);
        layoutIndicator = findViewById(R.id.layoutIndicators);

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

    //폴더에 있는 이미지 전부 가져와서 동적으로 뿌려준다.
    private void loadAllPhoto() {
        storageRef.child("photos").listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        int i = 1;
                        // 폴더 내의 item이 동날 때까지 모두 가져온다.
                        for (StorageReference item : listResult.getItems()) {

                            // imageview와 textview를 생성할 레이아웃 id 받아오기
                            LinearLayout layout = (LinearLayout) findViewById(R.id.maskImageLayout);
                            // textview 동적생성
                            TextView tv = new TextView(UserListActivity.this);
                            tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            tv.setText(Integer.toString(i) + "번 이미지 / 파일명 : " + item.getName());
                            tv.setTextSize(30);
                            tv.setTextColor(0xff004497);
                            layout.addView(tv);

                            //imageview 동적생성
                            ImageView iv = new ImageView(UserListActivity.this);
                            iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            layout.addView(iv);
                            i++; // 구현에는 의미 없는 코드.. 내 프로젝트에만 필요함

                            // reference의 item(이미지) url 받아오기
                            item.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if (task.isSuccessful()) {
                                        // Glide 이용하여 이미지뷰에 로딩
                                        Glide.with(UserListActivity.this)
                                                .load(task.getResult())
                                                .into(iv);
                                    } else {
                                        // URL을 가져오지 못하면 토스트 메세지
                                        Toast.makeText(UserListActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Uh-oh, an error occurred!
                                    Toast.makeText(UserListActivity.this, "File Get Error!!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Uh-oh, an error occurred!
                        Toast.makeText(UserListActivity.this, "File Get Error!!2", Toast.LENGTH_SHORT).show();
                    }
                });

    }


}
