package com.example.fragmentdemo;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private TextView tvUserInfo;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initViews();
        setupClickListeners();

        // 场景A: Activity → Activity 接收数据
        receiveDataFromMainActivity();
    }

    private void initViews() {
        tvUserInfo = findViewById(R.id.tvUserInfo);
        btnBack = findViewById(R.id.btnBack);
    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> finish());
    }

    private void receiveDataFromMainActivity() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String userName = bundle.getString("user_name", "未知");
            int userAge = bundle.getInt("user_age", 0);
            boolean isStudent = bundle.getBoolean("is_student", false);

            String userInfo = String.format(
                    "用户名: %s\n年龄: %d\n是否学生: %s",
                    userName, userAge, isStudent ? "是" : "否"
            );
            tvUserInfo.setText(userInfo);
        }
    }
}