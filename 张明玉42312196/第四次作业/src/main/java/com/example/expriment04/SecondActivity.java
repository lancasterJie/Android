package com.example.expriment04;

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

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initComponents();
    }
    void initComponents() {
        back = findViewById(R.id.back);
        isSave = findViewById(R.id.is_save);
        password = findViewById(R.id.password);
        account = findViewById(R.id.account);
        back.setOnClickListener((v) -> {
            SharedPreferences sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (isSave.isChecked()) {
                editor.putString("account", account.getText().toString());
                editor.putString("password", password.getText().toString());
                editor.putBoolean("is_save", true);
                editor.apply();
            }
            else {
                editor.putBoolean("is_save", false);
                editor.commit();
            }
            finish();
        });
    }
    private Button back;
    private CheckBox isSave;
    private EditText password, account;
}