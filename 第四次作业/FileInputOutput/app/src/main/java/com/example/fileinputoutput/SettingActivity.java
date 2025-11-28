package com.example.fileinputoutput;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SettingActivity extends AppCompatActivity {
    private EditText etUserName;
    private EditText etPassword;
    private CheckBox cbAutoSave;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_setting);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        cbAutoSave = findViewById(R.id.cb_auto_save);
        etUserName = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);

        btnLogin = findViewById(R.id.btn_login);

        SharedPreferences sp = getSharedPreferences("settings", MODE_PRIVATE);
        boolean autoSave = sp.getBoolean("auto_save", false);
        String userName = sp.getString("user_name", "User");
        String password = sp.getString("password", "123456");

        cbAutoSave.setChecked(autoSave);
        etUserName.setText(userName);
        etPassword.setText(password);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(SettingActivity.this, MainActivity.class);

                SharedPreferences sp = getSharedPreferences("settings", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("auto_save", cbAutoSave.isChecked());
                editor.putString("user_name", etUserName.getText().toString());
                editor.putString("password", etPassword.getText().toString());
                editor.apply();

                startActivity(it);
            }
        });

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        Log.d("ssssss", "111");

        SharedPreferences sp = getSharedPreferences("settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("auto_save", cbAutoSave.isChecked());
        editor.putString("user_name", etUserName.getText().toString());
        editor.putString("password", etPassword.getText().toString());
        editor.apply();
    }
}