package com.example.homework4;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class RecordDetailActivity extends AppCompatActivity {
    private TextView detailTitleTextView;
    private TextView detailTimeTextView;
    private TextView detailContentTextView;
    private Button editButton;
    private Button deleteButton;

    private Record record;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);

        detailTitleTextView = findViewById(R.id.detailTitleTextView);
        detailTimeTextView = findViewById(R.id.detailTimeTextView);
        detailContentTextView = findViewById(R.id.detailContentTextView);
        editButton = findViewById(R.id.editButton);
        deleteButton = findViewById(R.id.deleteButton);

        dbHelper = new DatabaseHelper(this);

        int recordId = getIntent().getIntExtra("record_id", -1);
        if (recordId != -1) {
            record = dbHelper.getRecordById(recordId);
            if (record != null) {
                displayRecord();
            }
        }

        editButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, RecordEditActivity.class);
            intent.putExtra("record_id", record.getId());
            startActivity(intent);
        });

        deleteButton.setOnClickListener(v -> showDeleteDialog());
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 刷新数据
        if (record != null) {
            record = dbHelper.getRecordById(record.getId());
            if (record != null) {
                displayRecord();
            }
        }
    }

    private void displayRecord() {
        detailTitleTextView.setText(record.getTitle());
        detailTimeTextView.setText(record.getTime());
        detailContentTextView.setText(record.getContent());
    }

    private void showDeleteDialog() {
        new AlertDialog.Builder(this)
                .setTitle("删除记录")
                .setMessage("确定要删除这条记录吗？")
                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.deleteRecord(record.getId());
                        finish(); // 返回上一页
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }
}