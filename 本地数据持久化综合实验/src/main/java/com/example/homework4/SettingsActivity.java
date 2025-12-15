package com.example.homework4;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SettingsActivity extends AppCompatActivity {
    private CheckBox autoSaveCheckbox;
    private EditText editUserName, editPassword;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        autoSaveCheckbox = findViewById(R.id.autoSaveCheckbox);
        editUserName = findViewById(R.id.editUserName);
        editPassword = findViewById(R.id.editPassword);
        loginButton = findViewById(R.id.loginButton);

        loadPreferences();

        autoSaveCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            savePreferences();
        });

        loginButton.setOnClickListener(v -> {
            // 处理登录逻辑
        });
    }

    private void loadPreferences() {
        SharedPreferences sp = getSharedPreferences("settings", MODE_PRIVATE);
        autoSaveCheckbox.setChecked(sp.getBoolean("auto_save", false));
        editUserName.setText(sp.getString("user_name", ""));
        editPassword.setText(sp.getString("passwd", ""));
    }

    private void savePreferences() {
        SharedPreferences sp = getSharedPreferences("settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("auto_save", autoSaveCheckbox.isChecked());
        editor.putString("user_name", editUserName.getText().toString());
        editor.putString("passwd", editPassword.getText().toString());
        editor.apply();
    }
}
