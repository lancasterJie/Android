package com.example.homework4;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
    private CheckBox autoSaveCheckBox;
    private EditText userNameEditText;
    private EditText passwordEditText;
    private Button saveSettingsButton;
    private Button loginButton;

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // 初始化视图
        autoSaveCheckBox = findViewById(R.id.autoSaveCheckBox);
        userNameEditText = findViewById(R.id.userNameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        saveSettingsButton = findViewById(R.id.saveSettingsButton);
        loginButton = findViewById(R.id.loginButton);

        // 获取SharedPreferences实例
        prefs = getSharedPreferences("settings", MODE_PRIVATE);

        // 加载已保存的设置
        loadSettings();

        // 设置按钮点击监听器
        saveSettingsButton.setOnClickListener(v -> saveSettings());
        loginButton.setOnClickListener(v -> {
            saveSettings();
            Intent intent = new Intent(SettingsActivity.this, RecordListActivity.class);
            startActivity(intent);
            Toast.makeText(SettingsActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
        });
    }

    private void loadSettings() {
        boolean autoSave = prefs.getBoolean("auto_save", false);
        String userName = prefs.getString("user_name", "");
        String password = prefs.getString("passwd", "");

        autoSaveCheckBox.setChecked(autoSave);
        userNameEditText.setText(userName);
        passwordEditText.setText(password);
    }

    private void saveSettings() {
        SharedPreferences.Editor editor = prefs.edit();

        editor.putBoolean("auto_save", autoSaveCheckBox.isChecked());
        editor.putString("user_name", userNameEditText.getText().toString());
        editor.putString("passwd", passwordEditText.getText().toString());

        editor.apply();

        Toast.makeText(this, "设置已保存", Toast.LENGTH_SHORT).show();
    }
}
