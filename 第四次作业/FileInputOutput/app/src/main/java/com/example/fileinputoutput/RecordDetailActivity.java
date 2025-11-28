package com.example.fileinputoutput;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RecordDetailActivity extends AppCompatActivity {

    private TextView tvTitle;
    private TextView tvTime;
    private TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_record_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 初始化控件
        tvTitle = findViewById(R.id.tv_title);
        tvTime = findViewById(R.id.tv_time);
        tvContent = findViewById(R.id.tv_content);

        // 获取传递过来的记录数据
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String title = extras.getString("record_title");
            String content = extras.getString("record_content");
            String time = extras.getString("record_time");

            // 设置数据到控件
            tvTitle.setText(title);
            tvTime.setText(time);
            tvContent.setText(content);
        }
    }
}