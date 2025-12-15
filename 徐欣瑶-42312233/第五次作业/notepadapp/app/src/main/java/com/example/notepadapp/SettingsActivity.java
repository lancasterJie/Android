package com.example.notepadapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        CheckBox autoSaveCheckBox = findViewById(R.id.autoSaveCheckBox);
        EditText userNameEditText = findViewById(R.id.userNameEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        Button saveButton = findViewById(R.id.saveButton);
        Button backButton = findViewById(R.id.backButton);

        // 加载已保存的设置
        SharedPreferences sp = getSharedPreferences("settings", MODE_PRIVATE);
        autoSaveCheckBox.setChecked(sp.getBoolean("auto_save", false));
        userNameEditText.setText(sp.getString("user_name", ""));
        passwordEditText.setText(sp.getString("passwd", ""));

        saveButton.setOnClickListener(v -> {
            // 保存设置
            boolean autoSave = autoSaveCheckBox.isChecked();
            String userName = userNameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("auto_save", autoSave);
            editor.putString("user_name", userName);
            editor.putString("passwd", password);
            editor.apply();

            Toast.makeText(this, "设置已保存", Toast.LENGTH_SHORT).show();
        });

        backButton.setOnClickListener(v -> {
            finish(); // 返回主界面
        });
    }
}