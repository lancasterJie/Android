package com.example.forthwork;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RecordDetailActivity extends AppCompatActivity {
    private TextView textViewDetailTitle, textViewDetailContent, textViewDetailTime;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);

        textViewDetailTitle = findViewById(R.id.textViewDetailTitle);
        textViewDetailContent = findViewById(R.id.textViewDetailContent);
        textViewDetailTime = findViewById(R.id.textViewDetailTime);

        dbHelper = new DatabaseHelper(this);

        // 获取传递过来的记录ID
        int recordId = getIntent().getIntExtra("record_id", -1);

        if (recordId != -1) {
            Record record = dbHelper.getRecord(recordId);
            if (record != null) {
                textViewDetailTitle.setText(record.getTitle());
                textViewDetailContent.setText(record.getContent());
                textViewDetailTime.setText("创建时间: " + record.getTime());
            }
        }
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}