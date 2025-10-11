package com.example.activitynavigator;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // 返回按钮
        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> {
            // 结束当前Activity，返回上一个Activity
            finish();
        });

        // 隐式跳转按钮
        Button btnImplicit = findViewById(R.id.btn_implicit);
        btnImplicit.setOnClickListener(v -> {
            // 创建隐式Intent
            Intent intent = new Intent();
            // 设置自定义Action
            intent.setAction("com.example.action.VIEW_THIRD_ACTIVITY");
            // 设置Category
            intent.addCategory(Intent.CATEGORY_DEFAULT);

            // 检查是否有Activity可以响应这个Intent
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        });
    }
}
