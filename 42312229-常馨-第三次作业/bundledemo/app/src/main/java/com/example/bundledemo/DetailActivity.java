package com.example.bundledemo;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView tvDetail = findViewById(R.id.tv_detail);

        // 从Intent中获取Bundle数据
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String userName = bundle.getString("user_name", "未知");
            int userAge = bundle.getInt("user_age", 0);
            boolean isStudent = bundle.getBoolean("is_student", false);

            String detail = String.format(
                    "用户信息:\n\n姓名: %s\n年龄: %d\n学生: %s",
                    userName, userAge, isStudent ? "是" : "否"
            );

            tvDetail.setText(detail);
        } else {
            tvDetail.setText("未接收到数据");
        }
    }
}