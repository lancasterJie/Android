package com.example.work4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    private static final String FILE_NAME = "note.txt";
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        Button btnSave = findViewById(R.id.btnSaveFile);
        Button btnLoad = findViewById(R.id.btnLoadFile);
        btnSave.setOnClickListener(v -> saveToFile());
        btnLoad.setOnClickListener(v -> loadFromFile());
    }

    private void saveToFile() {
        try (FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE)) {
            fos.write(editText.getText().toString().getBytes(StandardCharsets.UTF_8));
            Toast.makeText(this, R.string.saved_to_file, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadFromFile() {
        try (FileInputStream fis = openFileInput(FILE_NAME)) {
            byte[] data = fis.readAllBytes();
            editText.setText(new String(data, StandardCharsets.UTF_8));
        } catch (Exception e) {
            Toast.makeText(this, R.string.file_not_found, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // ✅ 修正：使用你提供的 main_menu.xml（支持 app:showAsAction）
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if (item.getItemId() == R.id.action_records) {
            startActivity(new Intent(this, RecordListActivity.class));
            return true;
        } else if (item.getItemId() == R.id.action_save_record) {
            String content = editText.getText().toString();
            if (!content.trim().isEmpty()) {
                MyDbHelper dbHelper = new MyDbHelper(this);
                dbHelper.insert(new Record(content));
                Toast.makeText(this, R.string.record_saved, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = getSharedPreferences("settings", MODE_PRIVATE);
        String userName = sp.getString("user_name", "Guest");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("欢迎，" + userName);
        }
        // ✅ 自动保存逻辑保留（符合要求 iv）
        if (sp.getBoolean("auto_save", false)) {
            saveToFile();
        }
    }
}