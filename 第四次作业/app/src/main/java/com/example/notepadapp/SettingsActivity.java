package com.example.notepadapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SettingsActivity extends AppCompatActivity {
    private CheckBox checkBoxAutoSave;
    private EditText editTextUserName;
    private EditText editTextPassword;
    private Button btnLogin;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // 初始化Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // 初始化SharedPreferences
        sp = getSharedPreferences("settings", MODE_PRIVATE);

        // 初始化视图
        checkBoxAutoSave = findViewById(R.id.checkBoxAutoSave);
        editTextUserName = findViewById(R.id.editTextUserName);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btnLogin);

        // 加载之前保存的配置
        loadSettings();

        // 保存设置按钮（在CheckBox状态改变时自动保存）
        checkBoxAutoSave.setOnCheckedChangeListener((buttonView, isChecked) -> {
            saveSettings();
        });

        // Login按钮
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings();
                Toast.makeText(SettingsActivity.this, "登录成功（跳转到主界面）", Toast.LENGTH_SHORT).show();
                // 可以在这里跳转到其他页面
                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 离开设置界面时保存设置
        saveSettings();
    }

    /**
     * 加载设置
     */
    private void loadSettings() {
        boolean autoSave = sp.getBoolean("auto_save", false);
        String userName = sp.getString("user_name", "");
        String password = sp.getString("passwd", "");

        checkBoxAutoSave.setChecked(autoSave);
        editTextUserName.setText(userName);
        editTextPassword.setText(password);
    }

    /**
     * 保存设置
     */
    private void saveSettings() {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("auto_save", checkBoxAutoSave.isChecked());
        editor.putString("user_name", editTextUserName.getText().toString());
        editor.putString("passwd", editTextPassword.getText().toString());
        editor.apply();
        Toast.makeText(this, "设置已保存", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}

