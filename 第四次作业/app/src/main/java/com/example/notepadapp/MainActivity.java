package com.example.notepadapp;

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

public class MainActivity extends AppCompatActivity {
    private EditText editTextNote;
    private Button btnSaveToFile;
    private Button btnLoadFromFile;
    private Button btnSaveToDatabase;
    private Toolbar toolbar;
    private static final String FILE_NAME = "note.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 初始化视图
        editTextNote = findViewById(R.id.editTextNote);
        btnSaveToFile = findViewById(R.id.btnSaveToFile);
        btnLoadFromFile = findViewById(R.id.btnLoadFromFile);
        btnSaveToDatabase = findViewById(R.id.btnSaveToDatabase);

        // 保存到文件按钮
        btnSaveToFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFile();
            }
        });

        // 从文件加载按钮
        btnLoadFromFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFromFile();
            }
        });

        // 保存到数据库按钮
        btnSaveToDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToDatabase();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 从SharedPreferences读取用户名并更新标题
        SharedPreferences sp = getSharedPreferences("settings", MODE_PRIVATE);
        String userName = sp.getString("user_name", "Guest");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("记事本 - " + userName);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 如果开启了自动保存，则自动保存到文件
        SharedPreferences sp = getSharedPreferences("settings", MODE_PRIVATE);
        boolean autoSave = sp.getBoolean("auto_save", false);
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

    /**
     * 保存文本到文件
     */
    private void saveToFile() {
        String text = editTextNote.getText().toString();
        try {
            FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(text.getBytes());
            fos.close();
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "保存失败: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 从文件加载文本
     */
    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILE_NAME);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            String text = new String(buffer);
            editTextNote.setText(text);
            Toast.makeText(this, "加载成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "文件不存在或读取失败", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 保存当前文本到数据库
     */
    private void saveToDatabase() {
        String content = editTextNote.getText().toString();
        if (content.trim().isEmpty()) {
            Toast.makeText(this, "内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        // 取前20个字符作为标题
        String title = content.length() > 20 ? content.substring(0, 20) : content;
        
        // 获取当前时间
        String time = java.text.DateFormat.getDateTimeInstance().format(new java.util.Date());

        MyDbHelper dbHelper = new MyDbHelper(this);
        long result = dbHelper.insertRecord(title, content, time);
        
        if (result > 0) {
            Toast.makeText(this, "保存到数据库成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "保存到数据库失败", Toast.LENGTH_SHORT).show();
        }
    }
}

