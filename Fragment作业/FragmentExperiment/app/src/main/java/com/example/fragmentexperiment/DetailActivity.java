package com.example.fragmentexperiment;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView tvUserInfo = findViewById(R.id.tv_user_info);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String username = extras.getString("username");
            int age = extras.getInt("age");
            boolean isStudent = extras.getBoolean("isStudent");

            String userInfo = String.format(
                    "用户名: %s\n年龄: %d\n是否学生: %s",
                    username, age, isStudent ? "是" : "否"
            );

            tvUserInfo.setText(userInfo);
        }
    }
}