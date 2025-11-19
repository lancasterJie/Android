package com.example.test3;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class SettingsActivity extends AppCompatActivity {

    private SwitchCompat switchAutoSave;
    private EditText editUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        switchAutoSave = findViewById(R.id.switchAutoSave);
        editUserName = findViewById(R.id.editUserName);
        Button btnSaveSettings = findViewById(R.id.btnSaveSettings);

        loadSettings();

        btnSaveSettings.setOnClickListener(v -> {
            saveSettings();
            finish();
        });
    }

    private void loadSettings() {
        SharedPreferences sp = getSharedPreferences("settings", MODE_PRIVATE);
        boolean autoSave = sp.getBoolean("auto_save", false);
        String userName = sp.getString("user_name", getString(R.string.default_user_name));
        switchAutoSave.setChecked(autoSave);
        editUserName.setText(userName);
    }

    private void saveSettings() {
        SharedPreferences sp = getSharedPreferences("settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("auto_save", switchAutoSave.isChecked());
        String inputName = editUserName.getText().toString().trim();
        if (inputName.isEmpty()) {
            inputName = getString(R.string.default_user_name);
        }
        editor.putString("user_name", inputName);
        editor.apply();
    }
}

