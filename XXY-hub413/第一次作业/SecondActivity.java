package com.example.activitynavigator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Button btnBackToMain = findViewById(R.id.btn_back_to_main);
        Button btnImplicitJump = findViewById(R.id.btn_implicit_jump);

        // 返回主页面
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
                Intent intent = new Intent();
                intent.setAction("com.example.action.VIEW_THIRD_ACTIVITY");
                intent.addCategory(Intent.CATEGORY_DEFAULT);

                // 检查是否有Activity可以处理这个Intent
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    // 如果没有Activity可以处理，使用显式跳转作为备选
                    Intent explicitIntent = new Intent(SecondActivity.this, ThirdActivity.class);
                    startActivity(explicitIntent);
                }
            }
        });
    }
}