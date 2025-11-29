package com.example.work4;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    private CheckBox checkAutoSave;
    private EditText editUserName, editPassword;
    private Button btnLogin;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initViews();
        sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);
        loadSettings();

        btnLogin.setOnClickListener(v -> saveSettings());
    }

    private void initViews() {
        checkAutoSave = findViewById(R.id.checkAutoSave);
        editUserName = findViewById(R.id.editUserName);
        editPassword = findViewById(R.id.editPassword);
        btnLogin = findViewById(R.id.btnLogin);
    }

    private void loadSettings() {
        boolean autoSave = sharedPreferences.getBoolean("auto_save", false);
        String userName = sharedPreferences.getString("user_name", "");
        String password = sharedPreferences.getString("passwd", "");

        checkAutoSave.setChecked(autoSave);
        editUserName.setText(userName);
        editPassword.setText(password);
    }

    private void saveSettings() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("auto_save", checkAutoSave.isChecked());
        editor.putString("user_name", editUserName.getText().toString());
        editor.putString("passwd", editPassword.getText().toString());
        editor.apply();

        // 返回主界面
        finish();
    }
}