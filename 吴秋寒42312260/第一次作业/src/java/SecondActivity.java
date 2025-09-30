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

    Button b6;  // 返回按钮（Button6）
    Button b7;  // 隐式跳转按钮（Button7）

    // 自定义Action常量（用于隐式跳转，需与Manifest中一致）
    private static final String ACTION_VIEW_THIRD = "com.example.action.VIEW_THIRD_ACTIVITY";
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
        b6 = findViewById(R.id.Button6);
        b7 = findViewById(R.id.Button7);
        // 定义内部类监听器（处理两个按钮的点击事件）
        class Mylistener implements View.OnClickListener {
            @Override
            public void onClick(View v) {
                // 判断点击的是哪个按钮
                if (v.getId() == R.id.Button6) {
                    // 点击返回按钮：结束当前Activity，返回MainActivity
                    finish();
                } else if (v.getId() == R.id.Button7) {
                    Intent intent = new Intent();
                    // 2. 设置自定义Action
                    intent.setAction(ACTION_VIEW_THIRD);
                    // 3. 添加默认Category（隐式跳转必须包含，否则无法匹配）
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    startActivity(intent);
                }
            }
        }
        b6.setOnClickListener(new Mylistener());
        b7.setOnClickListener(new Mylistener());
    }
}