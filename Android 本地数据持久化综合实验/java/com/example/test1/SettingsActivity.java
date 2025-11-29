package com.example.test1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private CheckBox cbAutoSave;
    private EditText etUserName, etPassword;
    private Button btnSave, btnLogin;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initViews();
        loadSettings();
        setupListeners();
    }

    private void initViews() {
        cbAutoSave = findViewById(R.id.cbAutoSave);
        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        btnSave = findViewById(R.id.btnSave);
        btnLogin = findViewById(R.id.btnLogin);

        sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);
    }

    private void loadSettings() {
        boolean autoSave = sharedPreferences.getBoolean("auto_save", false);
        String userName = sharedPreferences.getString("user_name", "");
        String password = sharedPreferences.getString("passwd", "");

        cbAutoSave.setChecked(autoSave);
        etUserName.setText(userName);
        etPassword.setText(password);
    }

    private void setupListeners() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void saveSettings() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("auto_save", cbAutoSave.isChecked());
        editor.putString("user_name", etUserName.getText().toString());
        editor.putString("passwd", etPassword.getText().toString());
        editor.apply();

        Toast.makeText(this, "设置已保存", Toast.LENGTH_SHORT).show();
    }

    private void login() {
        Toast.makeText(this, "登录功能", Toast.LENGTH_SHORT).show();
    }
}