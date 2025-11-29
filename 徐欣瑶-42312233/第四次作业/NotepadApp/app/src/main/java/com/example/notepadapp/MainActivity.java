package com.example.notepadapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private Button btnSaveFile, btnLoadFile, btnSaveDB, btnSettings, btnRecords;
    private MyDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        btnSaveFile = findViewById(R.id.btnSaveFile);
        btnLoadFile = findViewById(R.id.btnLoadFile);
        btnSaveDB = findViewById(R.id.btnSaveDB);
        btnSettings = findViewById(R.id.btnSettings);
        btnRecords = findViewById(R.id.btnRecords);

        dbHelper = new MyDbHelper(this);

        btnSaveFile.setOnClickListener(v -> saveToFile());
        btnLoadFile.setOnClickListener(v -> loadFromFile());
        btnSaveDB.setOnClickListener(v -> saveToDatabase());
        btnSettings.setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));
        btnRecords.setOnClickListener(v -> startActivity(new Intent(this, RecordListActivity.class)));
    }

    private void saveToFile() {
        String text = editText.getText().toString();
        if (text.isEmpty()) {
            Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            FileOutputStream fos = openFileOutput("note.txt", MODE_PRIVATE);
            fos.write(text.getBytes());
            fos.close();
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput("note.txt");
            byte[] data = new byte[fis.available()];
            fis.read(data);
            fis.close();
            String text = new String(data);
            editText.setText(text);
            Toast.makeText(this, "加载成功", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "文件不存在", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveToDatabase() {
        String content = editText.getText().toString();
        if (content.isEmpty()) {
            Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
            return;
        }

        String title = content.length() > 10 ? content.substring(0, 10) + "..." : content;
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());

        dbHelper.addRecord(title, content, time);
        Toast.makeText(this, "已保存到数据库", Toast.LENGTH_SHORT).show();
        editText.setText(""); // 清空输入框
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 自动加载设置
        SharedPreferences sp = getSharedPreferences("settings", MODE_PRIVATE);
        String userName = sp.getString("user_name", "用户");
        setTitle(userName + "的记事本");
    }
}