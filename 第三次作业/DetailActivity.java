package com.example.activityradiogroup;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.activityradiogroup.R;

public class DetailActivity extends AppCompatActivity {

    private Button btnBackToMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // 接收来自MainActivity的数据
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String username = extras.getString("username", "");
            int age = extras.getInt("age", 0);
            boolean isStudent = extras.getBoolean("isStudent", false);

            TextView tvInfo = findViewById(R.id.tv_info);
            String info = String.format("用户名: %s\n年龄: %d\n是否学生: %s",
                    username, age, isStudent ? "是" : "否");
            tvInfo.setText(info);
        }

        // 设置返回按钮
        btnBackToMain = findViewById(R.id.btn_back_to_main);
        btnBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 结束当前Activity，返回到MainActivity
                finish();
            }
        });
    }
}