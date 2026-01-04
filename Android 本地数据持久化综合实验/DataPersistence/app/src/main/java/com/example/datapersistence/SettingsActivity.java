package com.example.datapersistence;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SettingsActivity extends AppCompatActivity {
    private CheckBox checkBoxAutoSave;
    private EditText editUserName, editPassword;
    private Button btnSave;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // 初始化Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("设置");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // 初始化视图
        checkBoxAutoSave = findViewById(R.id.checkBoxAutoSave);
        editUserName = findViewById(R.id.editUserName);
        editPassword = findViewById(R.id.editPassword);
        btnSave = findViewById(R.id.btnSave);

        // 获取SharedPreferences
        sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);

        // 加载保存的设置
        loadSettings();

        // 设置保存按钮点击事件
        btnSave.setOnClickListener(v -> saveSettings());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    // 加载设置
    private void loadSettings() {
        boolean autoSave = sharedPreferences.getBoolean("auto_save", false);
        String userName = sharedPreferences.getString("user_name", "");
        String password = sharedPreferences.getString("password", "");

        checkBoxAutoSave.setChecked(autoSave);
        editUserName.setText(userName);
        editPassword.setText(password);
    }

    // 保存设置
    private void saveSettings() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("auto_save", checkBoxAutoSave.isChecked());
        editor.putString("user_name", editUserName.getText().toString());
        editor.putString("password", editPassword.getText().toString());
        editor.apply();

        Toast.makeText(this, "设置已保存", Toast.LENGTH_SHORT).show();
        finish();
    }
}
