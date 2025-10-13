package com.example.lifecycle;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * 普通Activity
 * 普通 Activity 跳转时，源 Activity 会进入 onStop 状态。
 * 返回按钮，调用 finish() 返回上一个 Activity。
 */
public class SecondActivity extends AppCompatActivity {

    private static final String TAG = "Jiayi"; // Log 标签

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "SecondActivity - onCreate");
        setContentView(R.layout.activity_second);

        // 处理系统栏内边距
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.second), (v, insets) -> {
            int systemBars = WindowInsetsCompat.Type.systemBars();
            v.setPadding(
                    insets.getInsets(systemBars).left,
                    insets.getInsets(systemBars).top,
                    insets.getInsets(systemBars).right,
                    insets.getInsets(systemBars).bottom
            );
            return insets;
        });

        // 返回按钮：关闭当前 Activity，返回 MainActivity
        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "SecondActivity - onStart"); // 即将可见
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "SecondActivity - onResume"); // 开始交互
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "SecondActivity - onPause"); // 暂停，失去焦点
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "SecondActivity - onStop"); // 完全不可见
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "SecondActivity - onDestroy"); // 即将销毁
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "SecondActivity - onRestart"); // 从停止状态重启
    }
}
