package com.example.myapplication4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    private CheckBox cbAutoSave;
    private EditText etUserName, etPassword;
    private Button btnSaveSettings, btnLogin;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initViews();
        sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);
        loadSettings();

        btnSaveSettings.setOnClickListener(v -> saveSettings());
        btnLogin.setOnClickListener(v -> login());
    }

    private void initViews() {
        cbAutoSave = findViewById(R.id.cbAutoSave);
        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        btnSaveSettings = findViewById(R.id.btnSaveSettings);
        btnLogin = findViewById(R.id.btnLogin);
    }

    private void loadSettings() {
        boolean autoSave = sharedPreferences.getBoolean("auto_save", false);
        String userName = sharedPreferences.getString("user_name", "");
        String password = sharedPreferences.getString("password", "");

        cbAutoSave.setChecked(autoSave);
        etUserName.setText(userName);
        etPassword.setText(password);
    }

    private void saveSettings() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("auto_save", cbAutoSave.isChecked());
        editor.putString("user_name", etUserName.getText().toString());
        editor.putString("password", etPassword.getText().toString());
        editor.apply();

        Toast.makeText(this, "设置已保存", Toast.LENGTH_SHORT).show();
    }

    private void login() {
        String userName = etUserName.getText().toString();
        String password = etPassword.getText().toString();

        if (userName.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "请输入用户名和密码", Toast.LENGTH_SHORT).show();
            return;
        }

        // 保存登录信息
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_name", userName);
        editor.putString("password", password);
        editor.apply();

        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
        finish(); // 返回主界面
    }
}