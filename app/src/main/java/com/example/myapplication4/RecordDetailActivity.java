package com.example.myapplication4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RecordDetailActivity extends AppCompatActivity {
    private TextView tvDetailTitle, tvDetailTime, tvDetailContent;
    private Button btnDelete;
    private MyDbHelper dbHelper;
    private int recordId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);

        initViews();
        dbHelper = new MyDbHelper(this);

        recordId = getIntent().getIntExtra("record_id", -1);
        if (recordId != -1) {
            loadRecordDetails();
        }

        btnDelete.setOnClickListener(v -> deleteRecord());
    }

    private void initViews() {
        tvDetailTitle = findViewById(R.id.tvDetailTitle);
        tvDetailTime = findViewById(R.id.tvDetailTime);
        tvDetailContent = findViewById(R.id.tvDetailContent);
        btnDelete = findViewById(R.id.btnDelete);
    }

    private void loadRecordDetails() {
        Record record = dbHelper.getRecord(recordId);
        if (record != null) {
            tvDetailTitle.setText(record.getTitle());
            tvDetailTime.setText(record.getTime());
            tvDetailContent.setText(record.getContent());
        }
    }

    private void deleteRecord() {
        dbHelper.deleteRecord(recordId);
        Toast.makeText(this, "记录已删除", Toast.LENGTH_SHORT).show();
        finish(); // 返回列表界面
    }
}