package com.kj.random_chatting.registInputPhotoPopup;

import static com.kj.random_chatting.common.Constants.MAX_UPLOAD_PICTURE_COUNT;

import android.app.Activity;
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
import com.kj.random_chatting.databinding.RegistInputPhotoActivityBinding;
import com.kj.random_chatting.databinding.RegistInputPhotoPopupActivityBinding;
import com.kj.random_chatting.registInputGender.RegistInputGenderActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class RegistInputPhotoPopupService extends Activity {
    private static final String TAG = "RegistInputPhotoPopupService";
    private RegistInputPhotoPopupActivityBinding binding;
    private Context context;
    private String choiceNumber;

    public RegistInputPhotoPopupService(Context mContext, RegistInputPhotoPopupActivityBinding mBinding, String mChoiceNumber) {
        Log.d(TAG, "Log : " + TAG + " -> RegistInputPhotoPopupService");

        context = mContext;
        binding = mBinding;
        choiceNumber = mChoiceNumber;
    }

    //확인 버튼 클릭
    public void closeModal(String choiceMode){
        //데이터 전달하기
        Intent intent = new Intent();
        intent.putExtra("choiceMode", choiceMode);
        intent.putExtra("choiceNumber", choiceNumber);
        setResult(RESULT_OK, intent);

        //액티비티(팝업) 닫기
        finish();
    }
    /**************************************************************
     *  버튼 클릭 이벤트 시작
     **************************************************************/

    public void btnUploadClick() {
        closeModal("UPLOAD");
    }

    public void btnDeleteClick() {
        closeModal("DELETE");
    }

    public void btnCancleClick() {
        closeModal("CANCLE");
    }

    /**************************************************************
     *  버튼 클릭 이벤트 끝
     **************************************************************/
}
