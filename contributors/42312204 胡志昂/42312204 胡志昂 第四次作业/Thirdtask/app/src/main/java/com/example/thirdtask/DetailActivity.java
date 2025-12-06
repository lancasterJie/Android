package com.example.thirdtask;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DetailActivity extends AppCompatActivity {
    private TextView textViewTitle;
    private TextView textViewContent;
    private TextView textViewTime;
    private RecordDao recordDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // 初始化Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 显示返回按钮
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // 初始化视图
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewContent = findViewById(R.id.textViewContent);
        textViewTime = findViewById(R.id.textViewTime);

        // 初始化数据库操作类
        recordDao = new RecordDao(this);

        // 获取传递的记录ID
        int recordId = getIntent().getIntExtra("record_id", -1);

        if (recordId != -1) {
            // 加载记录详情
            Record record = recordDao.getRecordById(recordId);
            if (record != null) {
                textViewTitle.setText(record.getTitle());
                textViewContent.setText(record.getContent());
                textViewTime.setText("创建时间: " + record.getTime());
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}