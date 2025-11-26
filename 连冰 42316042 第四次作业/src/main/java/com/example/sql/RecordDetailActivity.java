package com.example.sql;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 展示单条记录的详情，可删除。
 */
public class RecordDetailActivity extends AppCompatActivity {

    public static final String EXTRA_RECORD_ID = "record_id";

    private database dbHelper;
    private long recordId = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);

        dbHelper = new database(this);
        recordId = getIntent().getLongExtra(EXTRA_RECORD_ID, -1);

        TextView textTitle = findViewById(R.id.detail_title);
        TextView textTime = findViewById(R.id.detail_time);
        TextView textContent = findViewById(R.id.detail_content);
        Button btnDelete = findViewById(R.id.btn_delete_record);

        btnDelete.setOnClickListener(v -> deleteRecord());

        if (recordId == -1) {
            Toast.makeText(this, R.string.error_record_not_found, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Record record = dbHelper.getRecord(recordId);
        if (record == null) {
            Toast.makeText(this, R.string.error_record_not_found, Toast.LENGTH_SHORT).show();
            finish();
        } else {
            textTitle.setText(record.getTitle());
            textTime.setText(record.getTime());
            textContent.setText(record.getContent());
        }
    }

    private void deleteRecord() {
        if (recordId == -1) {
            return;
        }
        int rows = dbHelper.deleteRecord(recordId);
        if (rows > 0) {
            Toast.makeText(this, R.string.tip_record_deleted, Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, R.string.error_delete_failed, Toast.LENGTH_SHORT).show();
        }
    }
}

