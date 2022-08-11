package com.example.random_chatting;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

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
    private ImageView ivArea;
    private Button btImageLoad, btDirectoryImageLoad;
    private TextView tvName, tvGender, tvAge, tvPhoneNumber;

    // 이미지 폴더 경로 참조
    private StorageReference storageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_list_activity);

        ivArea = (ImageView) findViewById(R.id.user_list_activity_iv_area);
        btDirectoryImageLoad = (Button) findViewById(R.id.bt_directory_image_load);

        tvName = (TextView) findViewById(R.id.user_list_activity_tv_name);
        tvGender = (TextView) findViewById(R.id.user_list_activity_tv_gender);
        tvAge = (TextView) findViewById(R.id.user_list_activity_tv_age);
        tvPhoneNumber = (TextView) findViewById(R.id.user_list_activity_tv_phone_number);

        storageRef = FirebaseStorage.getInstance().getReference();

        /**
         * 정보 조회 시작
         */
        Call<String> call = Retrofit_client.getApiService().findUserInformation(
                "select");

        call.enqueue(new Callback<String>(){
            //콜백 받는 부분
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String jsonResponse = response.body();
                try {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.optString("status").equals("true")){
                        //Toast.makeText(UserListActivity.this, "File Get Error!!2", Toast.LENGTH_SHORT).show();
                        JSONArray jsonArray = jsonObject.getJSONArray("result");
                        List<TaskDTO.findUserInformationOutputDTO> findUserInfomationList = new ArrayList<>();

                        for (int i=0; i < jsonArray.length(); i++)
                        {
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

                        //정보 표시 (비동기 처리로 수정 해야함.)
                        showInformation(findUserInfomationList.get(0));

                        Toast.makeText(UserListActivity.this, "조회 성공", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(UserListActivity.this, "조회 실패 : " + jsonObject.optString("query"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(UserListActivity.this, "조회 실패 (catch) : " + e.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(UserListActivity.this, "조회 실패 (onFailure)", Toast.LENGTH_LONG).show();
            }
        });

        /**
         * 정보 조회 끝
         */

        btDirectoryImageLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAllPhoto();
            }
        });

    }

    private void showInformation(TaskDTO.findUserInformationOutputDTO info){
        tvName.setText(info.getUserName());
        tvGender.setText(info.getGender());
        tvAge.setText(info.getAge());
        tvPhoneNumber.setText(info.getPhoneNumber());

        if(info.getFileNameList().size() > 0) {
            showPhoto(info.getFileNameList().get(0));
        }else{
            Toast.makeText(getApplicationContext(), "이미지가 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    //이미지 slide 추가 해야함.
    private void showPhoto(String fileName){
        String photoDir = "photos/";
        String photoFullPath = photoDir + fileName;
        storageRef.child(photoFullPath).getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getApplicationContext())
                        .load(uri)
                        .into(ivArea);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                //이미지 로드 실패시
                Toast.makeText(getApplicationContext(), "이미지 로드 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //폴더에 있는 이미지 전부 가져와서 동적으로 뿌려준다.
    private void loadAllPhoto(){
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
                            tv.setText(Integer.toString(i)+"번 이미지 / 파일명 : " + item.getName());
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
