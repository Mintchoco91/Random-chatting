package com.kj.random_chatting.util;

import static android.content.Context.MODE_PRIVATE;

import static com.kj.random_chatting.common.Constants.SHARED_PREFERENCES_NAME;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.kj.random_chatting.common.Enum;
import com.kj.random_chatting.userChattingRoomList.UserChattingRoomListDTO;
import com.kj.random_chatting.userChattingRoomList.UserChattingRoomListFragment;
import com.kj.random_chatting.databinding.FragmentUserChattingRoomListBinding;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 공통으로 사용 할 Util 함수 정의
 */

public class UtilClass {

    private UtilClass utilClass;
    /**
     * textView 스크롤바 넘어갈 시 밑으로 자동 스크롤처리
     * [textView객체].setMovementMethod(new ScrollingMovementMethod()) 선언 후 사용 할 것
     */

    public void scrollBottom(TextView textView) {
        int lineTop = textView.getLayout().getLineTop(textView.getLineCount());
        int scrollY = lineTop - textView.getHeight();
        if (scrollY > 0) {
            textView.scrollTo(0, scrollY);
        } else {
            textView.scrollTo(0, 0);
        }
    }

    /**
     * 해당 자리수 까지 랜덤 처리
     * @param digit : 자릿수
     */
    public Integer createRandomNumber(Integer digit){
        Random random = new Random();

        String strMaxNumber = String.format("%0"+digit+"d", 0);
        Integer maxNumber = Integer.parseInt(strMaxNumber.replace("0","9"));
        Integer randNumber = random.nextInt(maxNumber);

        return randNumber;
    }

    public String generateRandomNumber(Integer digit){
        Random random = new Random();
        Integer maxNumber = 10; // 한자리당 9까지 나오도록
        String randomValue = "";
        Integer generateNumber;
        for(int i = 0; i < digit ; i++){
            generateNumber = random.nextInt(maxNumber);
            randomValue = randomValue + generateNumber;
        }
        return randomValue;
    }



    // get Pref (return Array)
    public ArrayList<String> getStringArrayPref(Context context, String key) {
        SharedPreferences prefs =  context.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        String json = prefs.getString(key, null);
        ArrayList<String> resultArray = new ArrayList<String>();
        if (json != null) {
            try {
                JSONArray a = new JSONArray(json);
                for (int i = 0; i < a.length(); i++) {
                    String url = a.optString(i);
                    resultArray.add(url);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return resultArray;
    }

    /**
     * PreferenceUtil 의 array Json 과 연계해서 사용.
     * @param jsonString
     * @param key
     * @return List<String>
     */
    public static List<String> getJsonToArray(String jsonString, String key) {
        List<String> arrayList = new ArrayList<>();
        if (jsonString != null) {
            try {
                JSONArray a = new JSONArray(jsonString);
                for (int i = 0; i < a.length(); i++) {
                    JSONObject obj = a.getJSONObject(i);
                    String keyData = obj.getString(key);
                    arrayList.add(keyData);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return arrayList;
    }

    public static String getMethodName() {
        return Thread.currentThread().getStackTrace()[4].getMethodName();
    }

    /**
     * 가장 기초 logger. 보통 메서드 생성 후 추가한다.
     * @param tag : 태그 명
     * @param methodName : 메서드 이름 (Thread.currentThread().getStackTrace()[2].getMethodName() -> 현재 메서드명 추출)
     * ex) UtilClass.basicWriteLog(TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
     */
    public static void basicWriteLog(String tag, String methodName){
        Logger.clearLogAdapters();
        Logger.addLogAdapter(new AndroidLogAdapter());
        Logger.d(tag, "MethodName : " + methodName);
    }

    /**
     * 로그 남길때 사용
     * @param tag : 태그 명
     * @param log : 로그 내용
     * @param type : Enum.LogType : D,E,W,V,I 타입 설정
     * ex) UtilClass.writeLog(TAG, "로그 내용", Enum.LogType.D);
     */
    public static void writeLog(String tag, String log, Enum.LogType type) {
        Logger.clearLogAdapters();
        Logger.addLogAdapter(new AndroidLogAdapter());


        switch(type){
            case D:
                Logger.d(tag, "Log : " + tag + " ->" + log);
                break;
            case E:
                Logger.e(tag, "Log : " + tag + " ->" + log);
                break;
            case W:
                Logger.w(tag, "Log : " + tag + " ->" + log);
                break;
            case V:
                Logger.v(tag, "Log : " + tag + " ->" + log);
                break;
            case I:
                Logger.i(tag, "Log : " + tag + " ->" + log);
                break;
        }
    }
}
