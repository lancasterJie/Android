package com.example.datapersistence;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class RecordDetailActivity extends AppCompatActivity {
    private TextView textTitle, textContent, textTime;
    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);

        // 初始化Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("记录详情");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // 初始化视图
        textTitle = findViewById(R.id.textDetailTitle);
        textContent = findViewById(R.id.textDetailContent);
        textTime = findViewById(R.id.textDetailTime);

        // 初始化数据库管理器
        dbManager = new DatabaseManager(this);
        dbManager.open();

        // 获取传递的记录ID
        int recordId = getIntent().getIntExtra("record_id", -1);

        if (recordId != -1) {
            loadRecord(recordId);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.close();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    // 加载记录详情
    private void loadRecord(int id) {
        Record record = dbManager.getRecordById(id);
        if (record != null) {
            textTitle.setText(record.getTitle());
            textContent.setText(record.getContent());
            textTime.setText(record.getTime());
        }
    }
}