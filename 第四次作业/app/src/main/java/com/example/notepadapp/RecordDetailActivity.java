package com.example.notepadapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class RecordDetailActivity extends AppCompatActivity {
    private TextView textViewTitle;
    private TextView textViewContent;
    private TextView textViewTime;
    private MyDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);

        // 初始化Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        textViewTitle = findViewById(R.id.textViewTitle);
        textViewContent = findViewById(R.id.textViewContent);
        textViewTime = findViewById(R.id.textViewTime);
        
        dbHelper = new MyDbHelper(this);

        // 获取传递过来的记录ID
        int recordId = getIntent().getIntExtra("record_id", -1);
        if (recordId != -1) {
            loadRecord(recordId);
        }
    }

    /**
     * 加载记录详情
     */
    private void loadRecord(int id) {
        Record record = dbHelper.getRecordById(id);
        if (record != null) {
            textViewTitle.setText(record.getTitle());
            textViewContent.setText(record.getContent());
            textViewTime.setText("时间: " + record.getTime());
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}

