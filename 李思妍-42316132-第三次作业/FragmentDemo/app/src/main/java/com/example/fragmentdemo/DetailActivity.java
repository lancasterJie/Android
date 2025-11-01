package com.example.fragmentdemo;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fragmentdemo.R;

public class DetailActivity extends AppCompatActivity {

    private TextView tvUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvUserInfo = findViewById(R.id.tvUserInfo);

        // 接收从MainActivity传递的数据
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String userName = bundle.getString("user_name", "未知");
            int userAge = bundle.getInt("user_age", 0);
            boolean isStudent = bundle.getBoolean("is_student", false);

            String userInfo = String.format(java.util.Locale.getDefault(), "用户名: %s\n年龄: %d\n是否学生: %s",
                    userName, userAge, isStudent ? "是" : "否");

            tvUserInfo.setText(userInfo);
        }
    }
}