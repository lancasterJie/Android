package com.example.homework4;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private Button saveButton, loadButton, saveRecordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        saveButton = findViewById(R.id.saveButton);
        loadButton = findViewById(R.id.loadButton);
        saveRecordButton = findViewById(R.id.saveRecordButton);

        saveButton.setOnClickListener(v -> saveToFile());
        loadButton.setOnClickListener(v -> loadFromFile());
        saveRecordButton.setOnClickListener(v -> saveRecord());
    }

    private void saveToFile() {
        try (FileOutputStream fos = openFileOutput("note.txt", MODE_PRIVATE)) {
            String text = editText.getText().toString();
            fos.write(text.getBytes());
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadFromFile() {
        try (FileInputStream fis = openFileInput("note.txt")) {
            byte[] buffer = new byte[1024];
            int length = fis.read(buffer);
            String text = new String(buffer, 0, length);
            editText.setText(text);
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "文件不存在", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "读取失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveRecord() {
        // 穿过用户输入的"Title"和"Content"，可以在这里存储到数据库
        // 例如: new MyDbHelper(this).insertRecord(title, content);
    }
}
