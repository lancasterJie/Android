package com.example.fragmenthomework;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    TextView tvUsername, tvAge, tvStudent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        tvUsername = findViewById(R.id.tv_username);
        tvAge = findViewById(R.id.tv_age);
        tvStudent = findViewById(R.id.tv_student);

        // 从 Intent 读取数据（场景 A）
        String username = getIntent().getStringExtra("username");
        int age = getIntent().getIntExtra("age", -1);
        boolean isStudent = getIntent().getBooleanExtra("isStudent", false);

        tvUsername.setText("用户名： " + username);
        tvAge.setText("年龄： " + age);
        tvStudent.setText("是否学生： " + (isStudent ? "是" : "否"));
    }
}
