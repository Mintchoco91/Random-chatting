package com.kj.random_chatting.util;

import android.app.Application;
import android.content.Context;

// 인터셉터(AuthInterceptor)에서 환경변수에 저장되어 있는 토큰값들을 사용해야 한다.
// 인터셉터는 액티비티가 아니기 때문에 Application Context에 접근할 수 없다.
// 이 클래스는 위 문제를 해결하기 위해 만든 클래스이다.
public class MyApp extends Application {
    private static MyApp instance;

    public static MyApp getInstance() {
        return instance;
    }

    public static Context getContext() {
        return instance;
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }
}
