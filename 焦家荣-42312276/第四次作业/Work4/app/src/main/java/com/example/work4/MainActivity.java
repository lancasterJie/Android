package com.example.work4;

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
import com.example.work4.db.MyDbHelper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private EditText editTextNote;
    private Button btnSaveToFile, btnLoadFromFile, btnSaveToDb;
    private Button btnGoToSettings, btnGoToRecords;
    private SharedPreferences sharedPreferences;
    private MyDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化工具栏
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();
        dbHelper = new MyDbHelper(this);
        sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);

        setupClickListeners();
    }

    private void initViews() {
        editTextNote = findViewById(R.id.editTextNote);
        btnSaveToFile = findViewById(R.id.btnSaveToFile);
        btnLoadFromFile = findViewById(R.id.btnLoadFromFile);
        btnSaveToDb = findViewById(R.id.btnSaveToDb);
        btnGoToSettings = findViewById(R.id.btnGoToSettings);
        btnGoToRecords = findViewById(R.id.btnGoToRecords);
    }

    private void setupClickListeners() {
        btnSaveToFile.setOnClickListener(v -> saveToFile());
        btnLoadFromFile.setOnClickListener(v -> loadFromFile());
        btnSaveToDb.setOnClickListener(v -> saveToDatabase());

        // 添加导航按钮点击事件
        btnGoToSettings.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        });

        btnGoToRecords.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, RecordListActivity.class));
        });
    }

    // 其他方法保持不变（saveToFile, loadFromFile, saveToDatabase, onResume）
    private void saveToFile() {
        String text = editTextNote.getText().toString();
        if (text.isEmpty()) {
            Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
            return;
        }

        try (FileOutputStream fos = openFileOutput("note.txt", MODE_PRIVATE)) {
            fos.write(text.getBytes());
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void loadFromFile() {
        try (FileInputStream fis = openFileInput("note.txt")) {
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            String text = new String(buffer);
            editTextNote.setText(text);
            Toast.makeText(this, "加载成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "文件不存在或读取失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void saveToDatabase() {
        String content = editTextNote.getText().toString();
        if (content.isEmpty()) {
            Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
            return;
        }

        // 生成标题（取前10个字符）
        String title = content.length() > 10 ? content.substring(0, 10) + "..." : content;
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                .format(new Date());

        android.content.ContentValues values = new android.content.ContentValues();
        values.put(MyDbHelper.COLUMN_TITLE, title);
        values.put(MyDbHelper.COLUMN_CONTENT, content);
        values.put(MyDbHelper.COLUMN_TIME, time);

        long result = dbHelper.getWritableDatabase().insert(
                MyDbHelper.TABLE_RECORDS,
                null,
                values
        );

        if (result != -1) {
            Toast.makeText(this, "记录保存成功", Toast.LENGTH_SHORT).show();
            editTextNote.setText("");
        } else {
            Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 读取用户名更新标题
        String userName = sharedPreferences.getString("user_name", "Guest");
        setTitle(userName + "的记事本");

        // 检查是否开启自动保存
        boolean autoSave = sharedPreferences.getBoolean("auto_save", false);
        if (autoSave && !editTextNote.getText().toString().isEmpty()) {
            saveToFile();
        }
    }

    // 保留菜单方法，工具栏会显示菜单按钮
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