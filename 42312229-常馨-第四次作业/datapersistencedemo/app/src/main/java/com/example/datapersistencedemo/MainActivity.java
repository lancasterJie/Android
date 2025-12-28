package com.example.datapersistencedemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private EditText editTextNote;
    private Button btnSaveToFile, btnLoadFromFile, btnSaveToDatabase;
    private DatabaseHelper dbHelper;
    private SharedPreferences sharedPreferences;
    private boolean autoSaveEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 设置Toolbar - 确保布局中有id为toolbar的控件
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 初始化视图
        initializeViews();

        // 初始化数据库和SharedPreferences
        dbHelper = new DatabaseHelper(this);
        sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);

        // 设置按钮监听器
        setupButtonListeners();

        // 从文件自动加载内容
        loadFromFile();
    }

    private void initializeViews() {
        editTextNote = findViewById(R.id.editTextNote);
        btnSaveToFile = findViewById(R.id.btnSaveToFile);
        btnLoadFromFile = findViewById(R.id.btnLoadFromFile);
        btnSaveToDatabase = findViewById(R.id.btnSaveToDatabase);
    }

    private void setupButtonListeners() {
        btnSaveToFile.setOnClickListener(v -> saveToFile());
        btnLoadFromFile.setOnClickListener(v -> loadFromFile());
        btnSaveToDatabase.setOnClickListener(v -> saveToDatabase());
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSettings();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (autoSaveEnabled && !editTextNote.getText().toString().trim().isEmpty()) {
            saveToFile();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if (id == R.id.action_record_list) {
            startActivity(new Intent(this, RecordListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveToFile() {
        String text = editTextNote.getText().toString().trim();

        if (text.isEmpty()) {
            Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
            return;
        }

        try (FileOutputStream fos = openFileOutput("note.txt", MODE_PRIVATE)) {
            fos.write(text.getBytes());
            Toast.makeText(this, "保存到文件成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "保存失败: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void loadFromFile() {
        try (FileInputStream fis = openFileInput("note.txt")) {
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            String text = new String(buffer);
            editTextNote.setText(text);
        } catch (IOException e) {
            // 文件不存在是正常情况，不提示用户
        }
    }

    private void saveToDatabase() {
        String content = editTextNote.getText().toString().trim();

        if (content.isEmpty()) {
            Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
            return;
        }

        // 生成标题（取前20个字符）
        String title = content.length() > 20 ? content.substring(0, 20) + "..." : content;

        // 获取当前时间
        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                .format(new Date());

        // 创建并保存记录
        Record record = new Record(title, content, currentTime);
        dbHelper.addRecord(record);

        Toast.makeText(this, "已保存为记录", Toast.LENGTH_SHORT).show();
        editTextNote.setText("");
    }

    private void loadSettings() {
        autoSaveEnabled = sharedPreferences.getBoolean("auto_save", false);
        String userName = sharedPreferences.getString("user_name", "Guest");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("记事本 - " + userName);
        }
    }
}