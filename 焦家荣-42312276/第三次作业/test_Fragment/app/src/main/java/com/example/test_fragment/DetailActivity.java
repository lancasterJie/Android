package com.example.test_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // 接收从 MainActivity 传递的数据
        Intent intent = getIntent();
        if (intent != null) {
            String username = intent.getStringExtra("username");
            int age = intent.getIntExtra("age", 0);
            boolean isStudent = intent.getBooleanExtra("isStudent", false);

            TextView textDetail = findViewById(R.id.text_detail);
            String userInfo = String.format(
                    "用户信息:\n\n用户名: %s\n年龄: %d\n学生: %s",
                    username, age, isStudent ? "是" : "否"
            );
            textDetail.setText(userInfo);
        }
    }
}