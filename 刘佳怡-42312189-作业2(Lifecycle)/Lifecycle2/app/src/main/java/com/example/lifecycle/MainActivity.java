package com.example.lifecycle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Android Activity 生命周期的基本流程。
 * 包含跳转到普通 Activity 和对话框式 Activity 的按钮。
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Jiayi"; // Log 标签，用于 Logcat 过滤

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "MainActivity - onCreate");
        setContentView(R.layout.activity_main);

        // 处理系统栏内边距
        View mainView = findViewById(R.id.main);
        ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
            int systemBars = WindowInsetsCompat.Type.systemBars();
            v.setPadding(
                    insets.getInsets(systemBars).left,
                    insets.getInsets(systemBars).top,
                    insets.getInsets(systemBars).right,
                    insets.getInsets(systemBars).bottom
            );
            return insets;
        });

        // 按钮点击：跳转到 SecondActivity（普通 Activity）
        findViewById(R.id.btn_to_second).setOnClickListener(v -> {
            Intent intent = new Intent(this, SecondActivity.class);
            startActivity(intent);
        });

        // 按钮点击：跳转到 DialogActivity（对话框式 Activity）
        findViewById(R.id.btn_to_dialog).setOnClickListener(v -> {
            Intent intent = new Intent(this, DialogActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "MainActivity - onStart"); // Activity 即将可见
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "MainActivity - onResume"); // Activity 开始与用户交互
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "MainActivity - onPause"); // Activity 失去焦点
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "MainActivity - onStop"); // Activity 完全不可见
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "MainActivity - onDestroy"); // Activity 即将被销毁
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "MainActivity - onRestart"); // Activity 从不可见状态重新启动
    }
}