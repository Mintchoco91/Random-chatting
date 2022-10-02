package com.kj.random_chatting.common;

import android.widget.TextView;

/**
 * 공통으로 사용 할 Util 함수 정의
 */

public class UtilClass {

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
}
