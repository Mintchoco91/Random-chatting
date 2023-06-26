package com.kj.random_chatting.util;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.kj.random_chatting.userChattingRoomList.UserChattingRoomListDTO;
import com.kj.random_chatting.userChattingRoomList.UserChattingRoomListFragment;
import com.kj.random_chatting.databinding.FragmentUserChattingRoomListBinding;

import org.json.JSONArray;
import org.json.JSONException;

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
            randomValue = randomValue + generateNumber.toString();
        }
        return randomValue.toString();
    }



    // get Pref (return Array)
    public ArrayList<String> getStringArrayPref(Context context, String key) {
        SharedPreferences prefs =  context.getSharedPreferences("token_prefs", MODE_PRIVATE);
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


    // get Pref (return String)
    public String getStringPref(Context context, String key) {
        SharedPreferences prefs =  context.getSharedPreferences("token_prefs", MODE_PRIVATE);
        String result = prefs.getString(key, null);
        return result;
    }

}
