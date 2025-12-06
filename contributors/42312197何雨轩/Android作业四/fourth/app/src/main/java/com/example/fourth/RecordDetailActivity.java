package com.example.fourth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RecordDetailActivity extends AppCompatActivity {

    private TextView tvDetailTitle;
    private TextView tvDetailTime;
    private TextView tvDetailContent;
    private Button btnEdit;
    private Button btnDeleteDetail;

    private MyDbHelper dbHelper;
    private int recordId;
    private Record currentRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_record_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        tvDetailTitle = findViewById(R.id.tvDetailTitle);
        tvDetailTime = findViewById(R.id.tvDetailTime);
        tvDetailContent = findViewById(R.id.tvDetailContent);
        btnEdit = findViewById(R.id.btnEdit);
        btnDeleteDetail = findViewById(R.id.btnDeleteDetail);

        // Initialize database helper
        dbHelper = new MyDbHelper(this);

        // Get record ID from intent
        recordId = getIntent().getIntExtra("record_id", -1);
        if (recordId == -1) {
            Toast.makeText(this, "记录不存在", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Set button click listeners
        btnEdit.setOnClickListener(v -> editRecord());
        btnDeleteDetail.setOnClickListener(v -> deleteRecord());
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRecord();
    }

    private void loadRecord() {
        currentRecord = dbHelper.getRecordById(recordId);
        if (currentRecord == null) {
            Toast.makeText(this, "记录不存在", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        tvDetailTitle.setText(currentRecord.getTitle());
        tvDetailTime.setText(currentRecord.getTime());
        tvDetailContent.setText(currentRecord.getContent());
    }

    private void editRecord() {
        Intent intent = new Intent(this, EditRecordActivity.class);
        intent.putExtra("record_id", recordId);
        startActivity(intent);
    }

    private void deleteRecord() {
        new AlertDialog.Builder(this)
                .setTitle("确认删除")
                .setMessage("确定要删除这条记录吗？")
                .setPositiveButton("删除", (dialog, which) -> {
                    dbHelper.deleteRecord(recordId);
                    Toast.makeText(this, "已删除", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton("取消", null)
                .show();
    }
}
