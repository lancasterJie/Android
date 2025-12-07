package com.example.file;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class RecordDetailActivity extends AppCompatActivity {

    private TextView tvTitle, tvTime, tvContent;
    private DatabaseHelper dbHelper;
    private int recordId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);

        // 初始化Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // 初始化视图
        tvTitle = findViewById(R.id.tvTitle);
        tvTime = findViewById(R.id.tvTime);
        tvContent = findViewById(R.id.tvContent);

        // 获取传递的记录ID
        recordId = getIntent().getIntExtra("record_id", -1);

        // 初始化数据库帮助类
        dbHelper = new DatabaseHelper(this);

        // 加载记录详情
        loadRecordDetail();
    }

    private void loadRecordDetail() {
        if (recordId != -1) {
            Record record = dbHelper.getRecordById(recordId);
            if (record != null) {
                tvTitle.setText(record.getTitle());
                tvTime.setText("创建时间: " + record.getTime());
                tvContent.setText(record.getContent());
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}