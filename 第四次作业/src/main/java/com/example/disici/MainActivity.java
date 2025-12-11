package com.example.disici;

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
import androidx.appcompat.widget.Toolbar;
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
    private SharedPreferences sharedPreferences;

    private static final String FILE_NAME = "note.txt";
    private static final String PREFS_NAME = "settings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 初始化视图
        etNote = findViewById(R.id.etNote);
        tvWelcome = findViewById(R.id.tvWelcome);
        btnSaveToFile = findViewById(R.id.btnSaveToFile);
        btnLoadFromFile = findViewById(R.id.btnLoadFromFile);
        btnSaveToDb = findViewById(R.id.btnSaveToDb);

        // 初始化SharedPreferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // 设置按钮点击事件
        btnSaveToFile.setOnClickListener(v -> saveToFile());
        btnLoadFromFile.setOnClickListener(v -> loadFromFile());
        btnSaveToDb.setOnClickListener(v -> saveToDatabase());
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 从SharedPreferences读取用户名并更新欢迎文本
        String userName = sharedPreferences.getString("user_name", "Guest");
        tvWelcome.setText("欢迎 " + userName + " 使用记事本");

        // 检查是否需要自动保存
        boolean autoSave = sharedPreferences.getBoolean("auto_save", false);
        if (autoSave) {
            saveToFile();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 检查是否需要自动保存
        boolean autoSave = sharedPreferences.getBoolean("auto_save", false);
        if (autoSave) {
            saveToFile();
        }
    }

    private void saveToFile() {
        String text = etNote.getText().toString().trim();
        if (text.isEmpty()) {
            Toast.makeText(this, "请输入内容再保存", Toast.LENGTH_SHORT).show();
            return;
        }

        try (FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE)) {
            fos.write(text.getBytes());
            Toast.makeText(this, "文件保存成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "文件保存失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadFromFile() {
        try (FileInputStream fis = openFileInput(FILE_NAME)) {
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            String text = new String(buffer);
            etNote.setText(text);
            Toast.makeText(this, "文件加载成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "文件不存在或读取失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveToDatabase() {
        String content = etNote.getText().toString().trim();
        if (content.isEmpty()) {
            Toast.makeText(this, "请输入内容再保存", Toast.LENGTH_SHORT).show();
            return;
        }

        // 生成标题（取前20个字符）
        String title = content.length() > 20 ? content.substring(0, 20) + "..." : content;

        // 生成当前时间
        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                .format(new Date());

        // 创建Record对象
        Record record = new Record(title, content, currentTime);

        // 保存到数据库
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        long result = dbHelper.insertRecord(record);

        if (result != -1) {
            Toast.makeText(this, "记录保存成功", Toast.LENGTH_SHORT).show();
            etNote.setText(""); // 清空输入框
        } else {
            Toast.makeText(this, "记录保存失败", Toast.LENGTH_SHORT).show();
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
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_records) {
            Intent intent = new Intent(this, RecordListActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}