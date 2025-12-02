package com.example.forthwork;

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

public class MainActivity extends AppCompatActivity {
    private EditText editTextContent;
    private Button btnSaveFile, btnLoadFile, btnSaveRecord;
    private SharedPreferences sharedPreferences;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // 确保使用正确的布局文件

        // 初始化Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 设置标题
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("记事本");
        }

        // 初始化视图
        editTextContent = findViewById(R.id.editTextContent);
        btnSaveFile = findViewById(R.id.btnSaveFile);
        btnLoadFile = findViewById(R.id.btnLoadFile);
        btnSaveRecord = findViewById(R.id.btnSaveRecord);

        // 初始化SharedPreferences和数据库
        sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);
        dbHelper = new DatabaseHelper(this);

        // 按钮点击事件
        btnSaveFile.setOnClickListener(v -> saveToFile());
        btnLoadFile.setOnClickListener(v -> loadFromFile());
        btnSaveRecord.setOnClickListener(v -> saveToDatabase());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if (id == R.id.action_records) {
            startActivity(new Intent(this, RecordListActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // 保存到文件
    private void saveToFile() {
        String content = editTextContent.getText().toString();
        if (content.isEmpty()) {
            Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
            return;
        }

        try (FileOutputStream fos = openFileOutput("note.txt", MODE_PRIVATE)) {
            fos.write(content.getBytes());
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
        }
    }

    // 从文件加载
    private void loadFromFile() {
        try (FileInputStream fis = openFileInput("note.txt")) {
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            String content = new String(buffer);
            editTextContent.setText(content);
            Toast.makeText(this, "加载成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "文件不存在", Toast.LENGTH_SHORT).show();
        }
    }

    // 保存到数据库
    private void saveToDatabase() {
        String content = editTextContent.getText().toString();
        if (content.isEmpty()) {
            Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
            return;
        }

        // 生成标题
        String title = content.length() > 10 ? content.substring(0, 10) + "..." : content;
        String currentTime = java.text.DateFormat.getDateTimeInstance().format(new java.util.Date());

        Record record = new Record(title, content, currentTime);
        long id = dbHelper.addRecord(record);

        if (id != -1) {
            Toast.makeText(this, "记录保存成功", Toast.LENGTH_SHORT).show();
            editTextContent.setText("");
        } else {
            Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}