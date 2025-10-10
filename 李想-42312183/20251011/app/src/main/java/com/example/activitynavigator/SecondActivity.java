package com.example.activitynavigator;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // --- 这部分代码你已经写对了，我帮你保留了 ---

        // 1. 找到“返回到主页”的按钮
        Button btnBackToMain = findViewById(R.id.btn_back_to_main);
        // 2. 给这个按钮设置点击事件
        btnBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 当按钮被点击时，结束当前的 SecondActivity，返回上一个界面（MainActivity）
                finish();
            }
        });

        // --- 从这里开始是我为你修改的部分 ---

        // 3. 找到“隐式跳转”的按钮
        Button btnImplicit = findViewById(R.id.btn_implicit);
        // 4. 给这个按钮设置点击事件，并添加隐式跳转逻辑
        btnImplicit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建一个隐式 Intent
                // Action 是一个字符串，用来描述你想做什么动作
                Intent intent = new Intent("com.example.action.VIEW_THIRD_ACTIVITY");

                // 添加一个默认的 Category
                intent.addCategory(Intent.CATEGORY_DEFAULT);

                // 启动 Activity
                startActivity(intent);
            }
        });

        // --- 修改结束 ---
    }
}