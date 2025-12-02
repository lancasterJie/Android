package com.example.dialectgame;

import android.app.Application;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

public class SpeechApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化讯飞SDK
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=8af9426b");
    }
}