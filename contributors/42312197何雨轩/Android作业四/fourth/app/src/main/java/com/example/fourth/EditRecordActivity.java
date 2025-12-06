package com.example.fourth;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditRecordActivity extends AppCompatActivity {

    private EditText etEditContent;
    private Button btnSaveEdit;

    private MyDbHelper dbHelper;
    private int recordId;
    private Record currentRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_record);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        etEditContent = findViewById(R.id.etEditContent);
        btnSaveEdit = findViewById(R.id.btnSaveEdit);

        // Initialize database helper
        dbHelper = new MyDbHelper(this);

        // Get record ID from intent
        recordId = getIntent().getIntExtra("record_id", -1);
        if (recordId == -1) {
            Toast.makeText(this, "记录不存在", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Load record
        loadRecord();

        // Set button click listener
        btnSaveEdit.setOnClickListener(v -> saveRecord());
    }

    private void loadRecord() {
        currentRecord = dbHelper.getRecordById(recordId);
        if (currentRecord == null) {
            Toast.makeText(this, "记录不存在", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        etEditContent.setText(currentRecord.getContent());
    }

    private void saveRecord() {
        String content = etEditContent.getText().toString().trim();
        if (content.isEmpty()) {
            Toast.makeText(this, "内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        // Generate new title from content
        String title = content.length() > 20 ? content.substring(0, 20) + "..." : content;

        // Get current time
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String time = sdf.format(new Date());

        // Update record
        int rowsAffected = dbHelper.updateRecord(recordId, title, content, time);
        if (rowsAffected > 0) {
            Toast.makeText(this, "已保存修改", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
        }
    }
}
