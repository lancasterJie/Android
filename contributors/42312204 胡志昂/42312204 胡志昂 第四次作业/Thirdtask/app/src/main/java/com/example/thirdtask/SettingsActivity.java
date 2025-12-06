package com.example.thirdtask;

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
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // 初始化Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 显示返回按钮
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // 初始化视图
        checkBoxAutoSave = findViewById(R.id.checkBoxAutoSave);
        editTextUserName = findViewById(R.id.editTextUserName);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btnLogin);

        // 初始化SharedPreferences
        sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);

        // 加载已保存的设置
        loadSettings();

        // 设置登录按钮点击监听器
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings();
                Toast.makeText(SettingsActivity.this, "设置已保存", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        saveSettings();
        finish();
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveSettings();
    }

    // 加载设置
    private void loadSettings() {
        boolean autoSave = sharedPreferences.getBoolean("auto_save", false);
        String userName = sharedPreferences.getString("user_name", "");
        String password = sharedPreferences.getString("password", "");

        checkBoxAutoSave.setChecked(autoSave);
        editTextUserName.setText(userName);
        editTextPassword.setText(password);
    }

    // 保存设置
    private void saveSettings() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("auto_save", checkBoxAutoSave.isChecked());
        editor.putString("user_name", editTextUserName.getText().toString());
        editor.putString("password", editTextPassword.getText().toString());
        editor.apply();
    }
}