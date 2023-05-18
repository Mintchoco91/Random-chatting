package com.kj.random_chatting.registInputPhoto;

import static com.kj.random_chatting.common.Constants.MAX_UPLOAD_PICTURE_COUNT;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kj.random_chatting.databinding.RegistInputInformationActivityBinding;
import com.kj.random_chatting.databinding.RegistInputPhotoActivityBinding;
import com.kj.random_chatting.registInputGender.RegistInputGenderActivity;
import com.kj.random_chatting.userRegist.UserRegistDTO;
import com.kj.random_chatting.userRegist.UserRegistInformationTaskRxJava;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class RegistInputPhotoService extends Activity {
    private static final String TAG = "RegistInputPhotoService";
    private RegistInputPhotoActivityBinding binding;
    private Context context;
    private HashMap<String, String> shareData = new HashMap<>();
    private String[] strFileNames = new String[MAX_UPLOAD_PICTURE_COUNT];
    private String[] strFileNameUri = new String[MAX_UPLOAD_PICTURE_COUNT];
    private Uri[] uriImgPaths = new Uri[MAX_UPLOAD_PICTURE_COUNT];

    private StorageReference storageRef;
    private StorageReference storageDirRef;

    public RegistInputPhotoService(Context mContext, RegistInputPhotoActivityBinding mBinding, HashMap<String, String> mShareData) {
        Log.d(TAG, "Log : " + TAG + " -> RegistInputPhotoService");

        context = mContext;
        binding = mBinding;
        shareData = mShareData;
    }

    public void uploadResult(int imgNo, int resultCode, Intent data) {
        uriImgPaths[imgNo] = data.getData();
        Log.d(TAG, "uri:" + String.valueOf(uriImgPaths[imgNo]));
        try {
            //Uri 파일을 Bitmap으로 만들어서 ImageView에 집어 넣는다.
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uriImgPaths[imgNo]);
            switch (imgNo) {
                case 0:
                    binding.registInputPhotoActivityBtnPicture0.setImageBitmap(bitmap);
                    break;
                case 1:
                    binding.registInputPhotoActivityBtnPicture1.setImageBitmap(bitmap);
                    break;
                case 2:
                    binding.registInputPhotoActivityBtnPicture2.setImageBitmap(bitmap);
                    break;
                case 3:
                    binding.registInputPhotoActivityBtnPicture3.setImageBitmap(bitmap);
                    break;
            }

            //fireBase Upload
            uploadFileAndRegistDB(uriImgPaths, imgNo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //파일 선택 후 결과 처리
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // requestCode 몇번째 이미지인지 확인용
        int imgNo = requestCode;

        if (resultCode == RESULT_OK) {
            uriImgPaths[imgNo] = data.getData();
            Log.d(TAG, "uri:" + String.valueOf(uriImgPaths[imgNo]));
            try {
                //Uri 파일을 Bitmap으로 만들어서 ImageView에 집어 넣는다.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriImgPaths[imgNo]);
                switch (imgNo) {
                    case 1:
                        binding.registInputPhotoActivityBtnPicture1.setImageBitmap(bitmap);
                        break;
                    case 2:
                        binding.registInputPhotoActivityBtnPicture2.setImageBitmap(bitmap);
                        break;
                    case 3:
                        binding.registInputPhotoActivityBtnPicture3.setImageBitmap(bitmap);
                        break;
                    case 4:
                        binding.registInputPhotoActivityBtnPicture4.setImageBitmap(bitmap);
                        break;
                }

                //fireBase Upload
                uploadFileAndRegistDB(uriImgPaths, imgNo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //upload the file
    private void uploadFileAndRegistDB(Uri[] uriImgPaths, Integer imgNo) {
        //업로드할 파일이 있으면 수행
        if (uriImgPaths[imgNo] != null) {
            //업로드 진행 Dialog 보이기
            final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("업로드중...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            //Unique한 파일명을 만들자.
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
            Date now = new Date();
            strFileNames[imgNo] = formatter.format(now) + ".png";
            //storage 주소와 폴더 파일명을 지정해 준다.
            storageRef = FirebaseStorage.getInstance().getReference();
            storageDirRef = storageRef.child("photos").child(strFileNames[imgNo]);
            String photoDir = "photos/";
            String photoFullPath = photoDir + strFileNames[imgNo];

            //업로드 시작
            storageDirRef.putFile(uriImgPaths[imgNo])
                    //성공시
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            storageRef.child(photoFullPath).getDownloadUrl()
                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            progressDialog.dismiss(); //업로드 진행 Dialog 상자 닫기
                                            strFileNameUri[imgNo] = uri.toString();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            //이미지 로드 실패시
                                            Toast.makeText(context, "이미지 로드 실패", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        }
                    })
                    //실패시
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(context, "업로드 실패!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    //진행중
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            //dialog에 진행률을 퍼센트로 출력해 준다
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "% ...");
                        }
                    });
        } else {
            Toast.makeText(context, "파일을 먼저 선택하세요.", Toast.LENGTH_SHORT).show();
        }
    }

    //가운데 빈 사진 앞으로 정렬 및 데이터 저장
    private void settingPicture(){
        List<String> lstFileName = new ArrayList<>(Arrays.asList(strFileNameUri));
        lstFileName.removeAll(Collections.singletonList(null));

        for (int i = 0; i < MAX_UPLOAD_PICTURE_COUNT; i++) {
            if (i < lstFileName.size()) {
                shareData.put("fileName"+i, lstFileName.get(i));
            } else {
                shareData.put("fileName"+i, null);
            }
        }
    }
    /**************************************************************
     *  버튼 클릭 이벤트 시작
     **************************************************************/

    public void btnContinueClick() {
        Log.d(TAG, "Log : " + TAG + "btnContinueClick");
        //validation 필요
        settingPicture();
        Intent intent = new Intent(context, RegistInputGenderActivity.class);
        intent.putExtra("shareData", shareData);
        context.startActivity(intent);
    }


    /**************************************************************
     *  버튼 클릭 이벤트 끝
     **************************************************************/
}
