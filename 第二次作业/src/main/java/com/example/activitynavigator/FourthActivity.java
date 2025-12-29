package com.example.activitynavigator;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class FourthActivity extends AppCompatActivity {
    public static final String TAG = "lifecycle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "FourthActivity-onCreate");

        // 设置对话框窗口属性
        Window window = getWindow();
        if (window != null) {
            window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
        }

        setContentView(R.layout.activity_fourth);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "FourthActivity-onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "FourthActivity-onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "FourthActivity-onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "FourthActivity-onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "FourthActivity-onDestroy");
    }
}