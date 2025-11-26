package com.example.sql;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 设置界面，管理 SharedPreferences 中的配置。
 */
public class SettingsActivity extends AppCompatActivity {

    private static final String PREF_NAME = "settings";
    private CheckBox checkAutoSave;
    private EditText editUserName;
    private EditText editPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        checkAutoSave = findViewById(R.id.check_auto_save);
        editUserName = findViewById(R.id.edit_user_name);
        editPassword = findViewById(R.id.edit_password);
        Button btnSave = findViewById(R.id.btn_save_settings);
        Button btnLogin = findViewById(R.id.btn_login_jump);

        btnSave.setOnClickListener(v -> {
            saveSettings();
            Toast.makeText(this, R.string.tip_settings_saved, Toast.LENGTH_SHORT).show();
        });

        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(this, RecordListActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSettings();
    }

    private void loadSettings() {
        SharedPreferences sp = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        boolean autoSave = sp.getBoolean("auto_save", false);
        String userName = sp.getString("user_name", "");
        String password = sp.getString("passwd", "");

        checkAutoSave.setChecked(autoSave);
        editUserName.setText(userName);
        editPassword.setText(password);
    }

    private void saveSettings() {
        SharedPreferences sp = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("auto_save", checkAutoSave.isChecked());
        editor.putString("user_name", editUserName.getText().toString());
        editor.putString("passwd", editPassword.getText().toString());
        editor.apply();
    }
}

