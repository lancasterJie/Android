package com.example.work4;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        CheckBox autoSave = findViewById(R.id.autoSave);
        EditText userNameEdit = findViewById(R.id.userNameEdit);
        EditText passwordEdit = findViewById(R.id.passwordEdit);
        Button loginBtn = findViewById(R.id.loginBtn);

        SharedPreferences sp = getSharedPreferences("settings", MODE_PRIVATE);
        autoSave.setChecked(sp.getBoolean("auto_save", false));
        userNameEdit.setText(sp.getString("user_name", ""));
        passwordEdit.setText(sp.getString("passwd", ""));

        loginBtn.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("auto_save", autoSave.isChecked());
            editor.putString("user_name", userNameEdit.getText().toString());
            editor.putString("passwd", passwordEdit.getText().toString());
            editor.apply();
            Toast.makeText(this, R.string.settings_saved, Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}