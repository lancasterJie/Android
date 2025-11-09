package com.example.fragmentdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView tvUserInfo = findViewById(R.id.tvUserInfo);
        Button btnBack = findViewById(R.id.btnBack);

        // 场景A: 接收从MainActivity传递过来的数据
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String userName = extras.getString("user_name", "未知");
            int age = extras.getInt("user_age", 0);
            boolean isStudent = extras.getBoolean("is_student", false);

            String userInfo = String.format("用户信息:\n\n用户名: %s\n年龄: %d\n是否学生: %s",
                    userName, age, isStudent ? "是" : "否");
            tvUserInfo.setText(userInfo);

            System.out.println("DetailActivity接收到的数据: " + userName + ", " + age + ", " + isStudent);
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}