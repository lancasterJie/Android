package com.fennuoyiman.homework001;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {


    // 定义自定义 Action
    public static final String ACTION_VIEW_THIRD_ACTIVITY = "com.fennuoyiman.activitynavigator.action.VIEW_THIRD_ACTIVITY";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Button btnBackToMain = findViewById(R.id.btn_back_to_main);

        // --- 第一部分：返回主页逻辑 ---
        btnBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // finish() 结束当前 Activity，返回到栈中的上一个 Activity (即 MainActivity)
                finish();
            }
        });

        // --- 第二部分的其他按钮逻辑将放在这里 ---

        Button btnImplicitToThird = findViewById(R.id.btn_implicit_to_third);

        // --- 第二部分：隐式跳转逻辑 ---
        btnImplicitToThird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建隐式Intent
                Intent intent = new Intent();
                // 设置自定义 Action
                intent.setAction(ACTION_VIEW_THIRD_ACTIVITY);
                // 设置默认 Category
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                // 启动 Activity
                startActivity(intent);
            }
        });
    }
}