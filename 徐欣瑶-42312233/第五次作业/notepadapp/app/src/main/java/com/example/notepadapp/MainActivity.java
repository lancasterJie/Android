package com.example.notepadapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private EditText contentEditText;
    private MyDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contentEditText = findViewById(R.id.contentEditText);
        Button saveFileButton = findViewById(R.id.saveFileButton);
        Button loadFileButton = findViewById(R.id.loadFileButton);
        Button saveRecordButton = findViewById(R.id.saveRecordButton);
        Button goSettingsButton = findViewById(R.id.goSettingsButton);
        Button goRecordsButton = findViewById(R.id.goRecordsButton);

        // 初始化数据库帮助类
        dbHelper = new MyDbHelper(this);

        saveFileButton.setOnClickListener(v -> saveToFile());
        loadFileButton.setOnClickListener(v -> loadFromFile());
        saveRecordButton.setOnClickListener(v -> saveAsRecord());
        goSettingsButton.setOnClickListener(v -> goToSettings());
        goRecordsButton.setOnClickListener(v -> goToRecords());

        loadUserName();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUserName();
        checkAutoSave();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    private void loadUserName() {
        SharedPreferences sp = getSharedPreferences("settings", MODE_PRIVATE);
        String userName = sp.getString("user_name", "Guest");
        // 可以设置标题，或者不做任何操作保持原样
    }

    private void checkAutoSave() {
        SharedPreferences sp = getSharedPreferences("settings", MODE_PRIVATE);
        boolean autoSave = sp.getBoolean("auto_save", false);
        if (autoSave && !contentEditText.getText().toString().trim().isEmpty()) {
            saveToFile();
        }
    }

    private void saveToFile() {
        String text = contentEditText.getText().toString();
        if (text.isEmpty()) {
            Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            FileOutputStream fos = openFileOutput("note.txt", MODE_PRIVATE);
            fos.write(text.getBytes());
            fos.close();
            Toast.makeText(this, "已保存到文件", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput("note.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            reader.close();
            contentEditText.setText(sb.toString());
            Toast.makeText(this, "已从文件加载", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "文件不存在", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveAsRecord() {
        String content = contentEditText.getText().toString().trim();
        if (content.isEmpty()) {
            Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
            return;
        }

        String title = content.length() > 10 ? content.substring(0, 10) + "..." : content;
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                .format(new Date());

        long id = dbHelper.addRecord(title, content, time);

        if (id > 0) {
            Toast.makeText(this, "已保存为记录", Toast.LENGTH_SHORT).show();
            contentEditText.setText("");
        } else {
            Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void goToSettings() {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    private void goToRecords() {
        startActivity(new Intent(this, RecordListActivity.class));
    }
}