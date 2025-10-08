package com.example.activitynavigator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    // 自定义Action常量
    public static final String ACTION_VIEW_THIRD_ACTIVITY = "com.example.activitynavigator.ACTION_VIEW_THIRD_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // 初始化视图
        Button btnBack = findViewById(R.id.btn_back);
        Button btnImplicit = findViewById(R.id.btn_implicit);

        // 返回按钮点击事件
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 结束当前Activity，返回MainActivity
                finish();
            }
        });

        // 隐式跳转按钮点击事件
        btnImplicit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建隐式Intent
                Intent intent = new Intent();
                intent.setAction(ACTION_VIEW_THIRD_ACTIVITY);
                intent.addCategory(Intent.CATEGORY_DEFAULT);

                // 启动Activity
                startActivity(intent);
            }
        });
    }
}