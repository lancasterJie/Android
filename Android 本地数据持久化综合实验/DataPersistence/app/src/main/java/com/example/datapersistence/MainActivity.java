package com.example.datapersistence;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
    private EditText editText;
    private Button btnSaveFile, btnLoadFile, btnSaveRecord;
    private DatabaseManager dbManager;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 初始化视图
        editText = findViewById(R.id.editText);
        btnSaveFile = findViewById(R.id.btnSaveFile);
        btnLoadFile = findViewById(R.id.btnLoadFile);
        btnSaveRecord = findViewById(R.id.btnSaveRecord);

        // 初始化数据库管理器
        dbManager = new DatabaseManager(this);
        dbManager.open();

        // 获取SharedPreferences
        sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);

        // 设置按钮点击事件
        btnSaveFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFile();
            }
        });

        btnLoadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFromFile();
            }
        });

        btnSaveRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToDatabase();
            }
        });

        // 从SharedPreferences加载用户名并更新标题
        updateTitleFromPreferences();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateTitleFromPreferences();

        // 检查是否需要自动保存
        if (sharedPreferences.getBoolean("auto_save", false)) {
            saveToFile();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbManager != null) {
            dbManager.close();
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
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.menu_records) {
            Intent intent = new Intent(this, RecordListActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // 保存到文件
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
        } catch (IOException e) {
            Toast.makeText(this, "保存失败: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    // 从文件加载
    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput("note.txt");
            int size = fis.available();
            byte[] buffer = new byte[size];
            fis.read(buffer);
            fis.close();
            String text = new String(buffer, "UTF-8");
            editText.setText(text);
            Toast.makeText(this, "加载成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "文件不存在或读取失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    // 保存到数据库
    private void saveToDatabase() {
        String content = editText.getText().toString();
        if (content.isEmpty()) {
            Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
            return;
        }

        // 创建标题（取前20个字符）
        String title;
        if (content.length() > 20) {
            title = content.substring(0, 20) + "...";
        } else {
            title = content;
        }

        // 获取当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String time = sdf.format(new Date());

        // 创建记录对象
        Record record = new Record(title, content, time);

        // 保存到数据库
        long id = dbManager.addRecord(record);

        if (id != -1) {
            Toast.makeText(this, "记录保存成功", Toast.LENGTH_SHORT).show();
            editText.setText(""); // 清空输入框
        } else {
            Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
        }
    }

    // 从SharedPreferences更新标题
    private void updateTitleFromPreferences() {
        String userName = sharedPreferences.getString("user_name", "Guest");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("欢迎, " + userName);
        }
    }
}