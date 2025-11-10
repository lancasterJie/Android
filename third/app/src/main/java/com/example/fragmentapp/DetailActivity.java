package com.example.fragmentapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fragmentapp.models.User;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView tvUserInfo = findViewById(R.id.tv_user_info);

        User user = (User) getIntent().getSerializableExtra("user");
        if (user != null) {
            String userInfo = String.format("姓名: %s\n年龄: %d\n是否学生: %s",
                    user.getName(), user.getAge(), user.isStudent() ? "是" : "否");
            tvUserInfo.setText(userInfo);
        }
    }
}