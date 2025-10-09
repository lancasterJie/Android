package com.example.experiment01;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ThirdActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_third);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editText = findViewById(R.id.editText);
        btnSendResult = findViewById(R.id.btnSendResult);
        btnSendResult.setOnClickListener((v) -> {
            setResult(101,
                    new Intent().putExtra("result", editText.getText().toString()));
            finish();
        });
        btnNoResult = findViewById(R.id.btnNoResult);
        btnNoResult.setOnClickListener((v) -> {
            setResult(ThirdActivity.RESULT_CANCELED);
            finish();
        });
    }

    private Button btnSendResult, btnNoResult;

    private EditText editText;
}