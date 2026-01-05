package com.example.homework4;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RecordEditActivity extends AppCompatActivity {
    private EditText titleEditText;
    private EditText contentEditText;
    private Button saveButton;

    private Record record;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_edit);

        titleEditText = findViewById(R.id.titleEditText);
        contentEditText = findViewById(R.id.contentEditText);
        saveButton = findViewById(R.id.saveButton);

        dbHelper = new DatabaseHelper(this);

        int recordId = getIntent().getIntExtra("record_id", -1);
        if (recordId != -1) {
            record = dbHelper.getRecordById(recordId);
            if (record != null) {
                titleEditText.setText(record.getTitle());
                contentEditText.setText(record.getContent());
            }
        }

        saveButton.setOnClickListener(v -> saveRecord());
    }

    private void saveRecord() {
        String title = titleEditText.getText().toString();
        String content = contentEditText.getText().toString();

        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "标题和内容都不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        // 更新记录
        record.setTitle(title);
        record.setContent(content);

        // 更新时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentTime = sdf.format(new Date());
        record.setTime(currentTime);

        // 保存到数据库
        int result = dbHelper.updateRecord(record);

        if (result > 0) {
            Toast.makeText(this, "更新成功", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "更新失败", Toast.LENGTH_SHORT).show();
        }
    }
}