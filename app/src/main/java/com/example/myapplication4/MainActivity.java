package com.example.myapplication4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private EditText etNote;
    private TextView tvWelcome;
    private Button btnSaveToFile, btnLoadFromFile, btnSaveToDb;
    private MyDbHelper dbHelper;
    private SharedPreferences sharedPreferences;

    private static final String FILE_NAME = "note.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        dbHelper = new MyDbHelper(this);
        sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);

        setupClickListeners();
        loadUserInfo();
    }

    private void initViews() {
        etNote = findViewById(R.id.etNote);
        tvWelcome = findViewById(R.id.tvWelcome);
        btnSaveToFile = findViewById(R.id.btnSaveToFile);
        btnLoadFromFile = findViewById(R.id.btnLoadFromFile);
        btnSaveToDb = findViewById(R.id.btnSaveToDb);
    }

    private void setupClickListeners() {
        btnSaveToFile.setOnClickListener(v -> saveToFile());
        btnLoadFromFile.setOnClickListener(v -> loadFromFile());
        btnSaveToDb.setOnClickListener(v -> saveToDatabase());
    }

    private void saveToFile() {
        String text = etNote.getText().toString();
        if (text.isEmpty()) {
            Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
            return;
        }

        try (FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE)) {
            fos.write(text.getBytes());
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "保存失败: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void loadFromFile() {
        try (FileInputStream fis = openFileInput(FILE_NAME)) {
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            String text = new String(buffer);
            etNote.setText(text);
            Toast.makeText(this, "加载成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "文件不存在或读取失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void saveToDatabase() {
        String content = etNote.getText().toString();
        if (content.isEmpty()) {
            Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
            return;
        }

        // 生成标题（内容的前10个字符）
        String title = content.length() > 10 ? content.substring(0, 10) + "..." : content;
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        Record record = new Record(title, content, time);
        dbHelper.addRecord(record);

        Toast.makeText(this, "已保存到数据库", Toast.LENGTH_SHORT).show();
        etNote.setText(""); // 清空输入框
    }

    private void loadUserInfo() {
        String userName = sharedPreferences.getString("user_name", "Guest");
        tvWelcome.setText("欢迎 " + userName);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUserInfo();

        // 如果开启了自动保存，则自动保存到文件
        boolean autoSave = sharedPreferences.getBoolean("auto_save", false);
        if (autoSave && !etNote.getText().toString().isEmpty()) {
            saveToFile();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if (id == R.id.menu_records) {
            startActivity(new Intent(this, RecordListActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}