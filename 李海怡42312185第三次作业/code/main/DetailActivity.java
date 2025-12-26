package com.example.test;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String userName = bundle.getString("user_name", "未知");
            int userAge = bundle.getInt("user_age", 0);
            boolean isStudent= bundle.getBoolean("is_student", false);

            TextView tvUserInfo = findViewById(R.id.tvUserInfo);
            String userInfo = String.format("用户名: %s\n年龄: %d\n是否学生: %s",
                    userName, userAge, isStudent ? "是" : "否");
            tvUserInfo.setText(userInfo);
        }

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
    }
}
