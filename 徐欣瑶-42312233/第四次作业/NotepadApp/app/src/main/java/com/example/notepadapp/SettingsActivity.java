package com.example.notepadapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    private CheckBox checkAutoSave;
    private EditText editUserName, editPassword;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        checkAutoSave = findViewById(R.id.checkAutoSave);
        editUserName = findViewById(R.id.editUserName);
        editPassword = findViewById(R.id.editPassword);
        btnSave = findViewById(R.id.btnSave);

        loadSettings();

        btnSave.setOnClickListener(v -> saveSettings());
    }

    private void loadSettings() {
        SharedPreferences sp = getSharedPreferences("settings", MODE_PRIVATE);
        checkAutoSave.setChecked(sp.getBoolean("auto_save", false));
        editUserName.setText(sp.getString("user_name", ""));
        editPassword.setText(sp.getString("password", ""));
    }

    private void saveSettings() {
        SharedPreferences sp = getSharedPreferences("settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("auto_save", checkAutoSave.isChecked());
        editor.putString("user_name", editUserName.getText().toString());
        editor.putString("password", editPassword.getText().toString());
        editor.apply();
        finish();
    }
}