package com.example.finalproject;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class GlobalApplication extends Application {
    private static GlobalApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        //네이티브 앱 키로 초기화
        KakaoSdk.init(this,"17c179f6ce1249336abda7f5a104e62f");
    }

}
