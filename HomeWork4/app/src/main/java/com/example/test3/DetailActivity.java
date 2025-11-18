package com.example.test3;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView tvInfo = findViewById(R.id.tv_info);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String userName = extras.getString("userName", "未知用户");
            int age = extras.getInt("age", 0);
            boolean isStudent = extras.getBoolean("isStudent", false);

            String info = String.format("用户名: %s\n年龄: %d\n学生: %s",
                    userName, age, isStudent ? "是" : "否");
            tvInfo.setText(info);
        }
    }
}