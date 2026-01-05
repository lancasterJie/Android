package com.example.homework4;

import androidx.appcompat.app.AppCompatActivity;
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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private EditText contentEditText;
    private TextView titleTextView;
    private Button saveToFileButton;
    private Button loadFromFileButton;
    private Button saveToDbButton;

    private static final String FILE_NAME = "note.txt";
    private static final String PREFS_NAME = "settings";
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化视图
        contentEditText = findViewById(R.id.contentEditText);
        titleTextView = findViewById(R.id.titleTextView);
        saveToFileButton = findViewById(R.id.saveToFileButton);
        loadFromFileButton = findViewById(R.id.loadFromFileButton);
        saveToDbButton = findViewById(R.id.saveToDbButton);

        // 初始化数据库
        dbHelper = new DatabaseHelper(this);

        // 设置按钮点击监听器
        saveToFileButton.setOnClickListener(v -> saveToFile());
        loadFromFileButton.setOnClickListener(v -> loadFromFile());
        saveToDbButton.setOnClickListener(v -> saveToDatabase());
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 从SharedPreferences加载用户名
        loadUserSettings();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 检查是否需要自动保存
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean autoSave = prefs.getBoolean("auto_save", false);
        if (autoSave) {
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

    private void loadUserSettings() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String userName = prefs.getString("user_name", "Guest");
        titleTextView.setText("欢迎，" + userName);
    }

    private void saveToFile() {
        String text = contentEditText.getText().toString();
        if (text.isEmpty()) {
            Toast.makeText(this, "内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(text.getBytes());
            fos.close();
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILE_NAME);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();

            String text = new String(buffer);
            contentEditText.setText(text);
            Toast.makeText(this, "加载成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "文件不存在或读取失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveToDatabase() {
        String content = contentEditText.getText().toString();
        if (content.isEmpty()) {
            Toast.makeText(this, "内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        // 生成标题（取前20个字符）
        String title = content.length() > 20 ? content.substring(0, 20) + "..." : content;

        // 获取当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentTime = sdf.format(new Date());

        // 创建记录对象
        Record record = new Record(title, content, currentTime);

        // 保存到数据库
        long result = dbHelper.addRecord(record);

        if (result != -1) {
            Toast.makeText(this, "记录保存成功", Toast.LENGTH_SHORT).show();
            contentEditText.setText(""); // 清空输入框
        } else {
            Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
        }
    }
}