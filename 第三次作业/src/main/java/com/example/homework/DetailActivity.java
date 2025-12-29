package com.example.homework;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    private TextView tvDetailInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvDetailInfo = findViewById(R.id.tv_detail_info);

        // 接收MainActivity传递的数据
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String info = "用户名：" + bundle.getString("user_name") + "\n" +
                    "年龄：" + bundle.getInt("user_age") + "\n" +
                    "是否学生：" + (bundle.getBoolean("is_student") ? "是" : "否");
            tvDetailInfo.setText(info);
        }
    }
}