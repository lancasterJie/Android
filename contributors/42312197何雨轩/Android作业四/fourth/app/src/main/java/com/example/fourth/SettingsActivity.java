package com.example.fourth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SettingsActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "settings";

    private CheckBox cbAutoSave;
    private EditText etUserName;
    private EditText etPassword;
    private Button btnSaveSettings;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        cbAutoSave = findViewById(R.id.cbAutoSave);
        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        btnSaveSettings = findViewById(R.id.btnSaveSettings);
        btnLogin = findViewById(R.id.btnLogin);

        // Load saved settings
        loadSettings();

        // Set button click listeners
        btnSaveSettings.setOnClickListener(v -> saveSettings());
        btnLogin.setOnClickListener(v -> login());
    }

    private void loadSettings() {
        SharedPreferences sp = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean autoSave = sp.getBoolean("auto_save", false);
        String userName = sp.getString("user_name", "");
        String password = sp.getString("passwd", "");

        cbAutoSave.setChecked(autoSave);
        etUserName.setText(userName);
        etPassword.setText(password);
    }

    private void saveSettings() {
        SharedPreferences sp = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        
        editor.putBoolean("auto_save", cbAutoSave.isChecked());
        editor.putString("user_name", etUserName.getText().toString().trim());
        editor.putString("passwd", etPassword.getText().toString());
        editor.apply();

        Toast.makeText(this, "设置已保存", Toast.LENGTH_SHORT).show();
    }

    private void login() {
        // Save settings before login if auto_save is checked
        if (cbAutoSave.isChecked()) {
            saveSettings();
        }

        String userName = etUserName.getText().toString().trim();
        String password = etPassword.getText().toString();

        if (userName.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "请输入用户名和密码", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save settings and navigate to MainActivity
        saveSettings();
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
        
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
