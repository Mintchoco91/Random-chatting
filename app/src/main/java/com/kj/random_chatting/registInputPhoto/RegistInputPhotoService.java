package com.kj.random_chatting.registInputPhoto;

import static com.kj.random_chatting.common.Constants.MAX_UPLOAD_PICTURE_COUNT;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kj.random_chatting.R;
import com.kj.random_chatting.databinding.RegistInputInformationActivityBinding;
import com.kj.random_chatting.databinding.RegistInputPhotoActivityBinding;
import com.kj.random_chatting.registInputEmailPw.RegistInputEmailPwActivity;
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

    public void prepareUpload(int imgNo, int resultCode, Intent data) {
        uriImgPaths[imgNo] = data.getData();
        Log.d(TAG, "uri:" + String.valueOf(uriImgPaths[imgNo]));
        try {
            //fireBase Upload
            uploadToFirebase(uriImgPaths, imgNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteImage(Integer choiceNumber) {
        selectImageView(choiceNumber).setImageBitmap(null);
        strFileNameUri[choiceNumber] = null;
    }

    //upload the file
    private void uploadToFirebase(Uri[] uriImgPaths, Integer imgNo) {
        //업로드할 파일이 있으면 수행
        if (uriImgPaths[imgNo] != null) {
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
                                            try {
                                                //Uri 파일을 Bitmap으로 만들어서 ImageView에 집어 넣는다.
                                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uriImgPaths[imgNo]);
                                                selectImageView(imgNo).setImageBitmap(bitmap);
                                            }catch(Exception e){
                                                Toast.makeText(context, "이미지 작업 실패 - Images.Media.getBitmap error", Toast.LENGTH_SHORT).show();
                                                binding.registInputPhotoActivityBtnContinue.setEnabled(true);
                                            }

                                            strFileNameUri[imgNo] = uri.toString();
                                            binding.registInputPhotoActivityBtnContinue.setEnabled(true);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            Toast.makeText(context, "이미지 작업 실패 - child addOnSuccessListener", Toast.LENGTH_SHORT).show();
                                            binding.registInputPhotoActivityBtnContinue.setEnabled(true);
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "이미지 작업 실패 - putFile addOnSuccessListener", Toast.LENGTH_SHORT).show();
                            binding.registInputPhotoActivityBtnContinue.setEnabled(true);
                        }
                    })
                    //진행중
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            //dialog에 진행률을 퍼센트로 출력해 준다
                            //progressDialog.setMessage("Uploaded " + ((int) progress) + "% ...");
                            Toast.makeText(context, "이미지 업로드 중 입니다.", Toast.LENGTH_SHORT).show();
                            binding.registInputPhotoActivityBtnContinue.setEnabled(false);
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

        for (int i = 0; i < MAX_UPLOAD_PICTURE_COUNT-1; i++) {
            if (i < lstFileName.size()) {
                shareData.put("fileName"+i, lstFileName.get(i));
            } else {
                shareData.put("fileName"+i, null);
            }
        }
    }

    private ImageView selectImageView(Integer choiceNumber){
        ImageView imageView = null;
        switch (choiceNumber) {
            case 0:
                imageView = binding.registInputPhotoActivityBtnPicture0;
                break;
            case 1:
                imageView = binding.registInputPhotoActivityBtnPicture1;
                break;
            case 2:
                imageView = binding.registInputPhotoActivityBtnPicture2;
                break;
            case 3:
                imageView = binding.registInputPhotoActivityBtnPicture3;
                break;
        }

        return imageView;
    }
    /**************************************************************
     *  버튼 클릭 이벤트 시작
     **************************************************************/

    public void btnContinueClick() {
        Log.d(TAG, "Log : " + TAG + "btnContinueClick");
        //validation 필요
        settingPicture();
        Intent intent = new Intent(context, RegistInputEmailPwActivity.class);
        intent.putExtra("shareData", shareData);
        context.startActivity(intent);
    }


    /**************************************************************
     *  버튼 클릭 이벤트 끝
     **************************************************************/
}
