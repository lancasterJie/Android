package com.example.forthwork;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    private CheckBox checkBoxAutoSave;
    private EditText editTextUserName, editTextPassword;
    private Button btnSaveSettings, btnLogin;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // 初始化视图
        checkBoxAutoSave = findViewById(R.id.checkBoxAutoSave);
        editTextUserName = findViewById(R.id.editTextUserName);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnSaveSettings = findViewById(R.id.btnSaveSettings);
        btnLogin = findViewById(R.id.btnLogin);

        // 初始化SharedPreferences
        sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);

        // 加载已保存的设置
        loadSettings();

        // 保存设置按钮
        btnSaveSettings.setOnClickListener(v -> saveSettings());

        // 登录按钮（示例）
        btnLogin.setOnClickListener(v -> {
            String username = editTextUserName.getText().toString();
            String password = editTextPassword.getText().toString();

            if (!username.isEmpty() && !password.isEmpty()) {
                Toast.makeText(this, "登录成功！", Toast.LENGTH_SHORT).show();
                // 可以在这里添加跳转到其他页面的代码
            } else {
                Toast.makeText(this, "请输入用户名和密码", Toast.LENGTH_SHORT).show();
            }
        });
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

        Toast.makeText(this, "设置已保存", Toast.LENGTH_SHORT).show();
    }
}