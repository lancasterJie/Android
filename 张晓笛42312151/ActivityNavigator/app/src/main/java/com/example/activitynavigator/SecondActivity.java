package com.example.activitynavigator;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {

    private Button btnBackToMain;
    private Button btnImplicitJump;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        initViews();
        setupClickListeners();
    }

    private void initViews() {
        btnBackToMain = findViewById(R.id.btn_back_to_main);
        btnImplicitJump = findViewById(R.id.btn_implicit_jump);
    }

    private void setupClickListeners() {
        // 返回主页
        btnBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 结束当前Activity，返回MainActivity
            }
        });

        // 隐式跳转到ThirdActivity
        btnImplicitJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建隐式Intent
                Intent intent = new Intent();
                intent.setAction("com.example.action.VIEW_THIRD_ACTIVITY");
                intent.addCategory(Intent.CATEGORY_DEFAULT);

                // 启动Activity
                startActivity(intent);
            }
        });
    }
}