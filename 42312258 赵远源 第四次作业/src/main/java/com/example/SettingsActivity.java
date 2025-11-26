package com.example;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 设置界面：负责维护 SharedPreferences 中的“自动保存、昵称、密码”三项配置。
 * 进入界面自动加载，离开或点击按钮时自动写回。
 */
public class SettingsActivity extends AppCompatActivity {

    private CheckBox checkAutoSave;
    private EditText editUserName;
    private EditText editPassword;
    private Button btnSave;
    private Button btnLogin;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);

        checkAutoSave = findViewById(R.id.cb_auto_save);
        editUserName = findViewById(R.id.et_user_name);
        editPassword = findViewById(R.id.et_password);
        btnSave = findViewById(R.id.btn_save_settings);
        btnLogin = findViewById(R.id.btn_login);

        // 点击“保存设置”立即保存并给予 Toast 提示
        btnSave.setOnClickListener(v -> {
            saveSettings();
            Toast.makeText(this, R.string.toast_settings_saved, Toast.LENGTH_SHORT).show();
        });

        // Login 只是示例：保存后返回主界面
        btnLogin.setOnClickListener(v -> {
            saveSettings();
            startActivity(new Intent(this, MainActivity.class));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSettings(); // 每次重新进入页面都刷新最新配置
    }

    @Override
    protected void onPause() {
        saveSettings(); // 离开前再保存一次，避免遗漏
        super.onPause();
    }

    private void loadSettings() {
        // 从 SharedPreferences 中读取数据并更新 UI
        boolean autoSave = sharedPreferences.getBoolean("auto_save", false);
        String userName = sharedPreferences.getString("user_name", "");
        String password = sharedPreferences.getString("passwd", "");

        checkAutoSave.setChecked(autoSave);
        editUserName.setText(userName);
        editPassword.setText(password);
    }

    private void saveSettings() {
        // 将 UI 当前状态写回 SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("auto_save", checkAutoSave.isChecked());
        editor.putString("user_name", editUserName.getText().toString().trim());
        editor.putString("passwd", editPassword.getText().toString());
        editor.apply();
    }
}
