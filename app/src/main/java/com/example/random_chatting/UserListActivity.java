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
    private Button btnNextUser;

    //page (1페이지 부터 시작)
    private Integer currentPageCnt = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_list_activity);
        btnNextUser = (Button) findViewById(R.id.user_list_activity_btn_next_user);

        FindUserInformationTaskRxJava findUserInformationTaskRxJava = new FindUserInformationTaskRxJava(this);
        findUserInformationTaskRxJava.runFunc();

        UserListService userListService = new UserListService(this);

        btnNextUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPageCnt < mainFindUserInfomationList.size()) {
                    userListService.showInformation(mainFindUserInfomationList.get(currentPageCnt));
                    currentPageCnt++;
                }else{
                    Toast.makeText(getApplicationContext(), "더 이상 회원이 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
