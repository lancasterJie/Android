package com.example.thirdtask;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private EditText editTextNote;
    private Button btnSaveToFile;
    private Button btnLoadFromFile;
    private Button btnSaveToDb;
    private SharedPreferences sharedPreferences;
    private boolean autoSaveEnabled = false;
    private RecordDao recordDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 初始化视图
        editTextNote = findViewById(R.id.editTextNote);
        btnSaveToFile = findViewById(R.id.btnSaveToFile);
        btnLoadFromFile = findViewById(R.id.btnLoadFromFile);
        btnSaveToDb = findViewById(R.id.btnSaveToDb);

        // 初始化SharedPreferences
        sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);

        // 初始化数据库操作类
        recordDao = new RecordDao(this);

        // 设置按钮点击监听器
        btnSaveToFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFile();
            }
        });

        btnLoadFromFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFromFile();
            }
        });

        btnSaveToDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToDatabase();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 从SharedPreferences读取配置并更新UI
        loadSettings();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 如果开启了自动保存，则保存到文件
        if (autoSaveEnabled) {
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

        if (id == R.id.menu_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if (id == R.id.menu_records) {
            startActivity(new Intent(this, RecordListActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // 保存到文件
    private void saveToFile() {
        String text = editTextNote.getText().toString();
        if (TextUtils.isEmpty(text)) {
            Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            FileOutputStream fos = openFileOutput("note.txt", MODE_PRIVATE);
            fos.write(text.getBytes());
            fos.close();
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "保存失败: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // 从文件加载
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
            fis.close();

            editTextNote.setText(sb.toString());
            Toast.makeText(this, "加载成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "文件不存在或读取失败", Toast.LENGTH_SHORT).show();
        }
    }

    // 保存到数据库
    private void saveToDatabase() {
        String content = editTextNote.getText().toString();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
            return;
        }

        // 获取前10个字符作为标题
        String title = content.length() > 10 ? content.substring(0, 10) + "..." : content;

        // 获取当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentTime = sdf.format(new Date());

        // 创建记录对象
        Record record = new Record(title, content, currentTime);

        // 保存到数据库
        long id = recordDao.addRecord(record);
        if (id != -1) {
            Toast.makeText(this, "已保存为记录", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
        }
    }

    // 从SharedPreferences加载设置
    private void loadSettings() {
        String userName = sharedPreferences.getString("user_name", "Guest");
        autoSaveEnabled = sharedPreferences.getBoolean("auto_save", false);

        // 更新标题栏
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("欢迎 " + userName);
        }
    }
}