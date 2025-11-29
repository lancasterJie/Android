package com.example.test1;

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
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private EditText etContent;
    private Button btnSaveToFile, btnLoadFromFile, btnSaveToDb;
    private SharedPreferences sharedPreferences;
    private MyDbHelper dbHelper;

    private static final String FILE_NAME = "note.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initData();
        setupListeners();
    }

    private void initViews() {
        etContent = findViewById(R.id.etContent);
        btnSaveToFile = findViewById(R.id.btnSaveToFile);
        btnLoadFromFile = findViewById(R.id.btnLoadFromFile);
        btnSaveToDb = findViewById(R.id.btnSaveToDb);
    }

    private void initData() {
        sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);
        dbHelper = new MyDbHelper(this);
    }

    private void setupListeners() {
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

    private void saveToFile() {
        String content = etContent.getText().toString();
        if (content.isEmpty()) {
            Toast.makeText(this, "内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(content.getBytes());
            fos.close();
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILE_NAME);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            String content = new String(buffer);
            etContent.setText(content);
            fis.close();
            Toast.makeText(this, "加载成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "文件不存在", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveToDatabase() {
        String content = etContent.getText().toString();
        if (content.isEmpty()) {
            Toast.makeText(this, "内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String title = content.length() > 10 ? content.substring(0, 10) + "..." : content;
        long result = dbHelper.addRecord(title, content);

        if (result != -1) {
            Toast.makeText(this, "已保存为记录", Toast.LENGTH_SHORT).show();
            etContent.setText("");
        } else {
            Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
        }
    }

    public void openSettings(View view) {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    public void openRecords(View view) {
        startActivity(new Intent(this, RecordListActivity.class));
    }
}