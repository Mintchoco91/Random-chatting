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
import com.kj.random_chatting.common.Enum;
import com.kj.random_chatting.databinding.RegistInputPhotoActivityBinding;
import com.kj.random_chatting.databinding.RegistInputPhotoPopupActivityBinding;
import com.kj.random_chatting.registInputGender.RegistInputGenderActivity;
import com.kj.random_chatting.util.UtilClass;

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
        UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());

        context = mContext;
        binding = mBinding;
        choiceNumber = mChoiceNumber;
    }

    /**************************************************************
     *  버튼 클릭 이벤트 시작
     **************************************************************/
    //Intent 관련 작업이라 Activity 단에서 처리 했음.

    /**************************************************************
     *  버튼 클릭 이벤트 끝
     **************************************************************/
}
