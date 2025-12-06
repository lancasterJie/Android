package com.example.fourth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fourth.RecordListActivity;
import com.example.fourth.SettingsActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String FILE_NAME = "note.txt";
    private static final String PREFS_NAME = "settings";

    private EditText etNote;
    private TextView tvWelcome;
    private Button btnSaveFile, btnLoadFile, btnSaveRecord;
    private Button btnSettings, btnRecordList;
    private MyDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        etNote = findViewById(R.id.etNote);
        tvWelcome = findViewById(R.id.tvWelcome);
        btnSaveFile = findViewById(R.id.btnSaveFile);
        btnLoadFile = findViewById(R.id.btnLoadFile);
        btnSaveRecord = findViewById(R.id.btnSaveRecord);

        // Initialize database helper
        dbHelper = new MyDbHelper(this);

        // Initialize navigation buttons
        btnSettings = findViewById(R.id.btnSettings);
        btnRecordList = findViewById(R.id.btnRecordList);

        // Set button click listeners
        btnSaveFile.setOnClickListener(v -> saveToFile());
        btnLoadFile.setOnClickListener(v -> loadFromFile());
        btnSaveRecord.setOnClickListener(v -> saveAsRecord());
        btnSettings.setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));
        btnRecordList.setOnClickListener(v -> startActivity(new Intent(this, RecordListActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Read SharedPreferences and update welcome message
        SharedPreferences sp = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String userName = sp.getString("user_name", "Guest");
        tvWelcome.setText("欢迎, " + userName);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Check if auto_save is enabled
        SharedPreferences sp = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
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

    private void saveToFile() {
        String text = etNote.getText().toString();
        try {
            FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(text.getBytes());
            fos.close();
            Toast.makeText(this, "已保存到文件", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "保存失败: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILE_NAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            reader.close();
            fis.close();
            
            // Remove trailing newline if exists
            String content = sb.toString();
            if (content.endsWith("\n")) {
                content = content.substring(0, content.length() - 1);
            }
            etNote.setText(content);
            Toast.makeText(this, "已从文件加载", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "文件不存在", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "加载失败: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void saveAsRecord() {
        String content = etNote.getText().toString().trim();
        if (content.isEmpty()) {
            Toast.makeText(this, "内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        // Generate title from first few characters
        String title = content.length() > 20 ? content.substring(0, 20) + "..." : content;
        
        // Get current time
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String time = sdf.format(new Date());

        // Insert into database
        long id = dbHelper.insertRecord(title, content, time);
        if (id > 0) {
            Toast.makeText(this, "已保存为记录", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "保存记录失败", Toast.LENGTH_SHORT).show();
        }
    }
}