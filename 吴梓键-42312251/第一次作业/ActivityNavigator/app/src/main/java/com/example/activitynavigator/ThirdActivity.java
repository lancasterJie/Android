package com.example.activitynavigator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ThirdActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        EditText etInput = findViewById(R.id.et_input);
        Button btnReturn = findViewById(R.id.btn_return_result);
        Button btnCancel = findViewById(R.id.btn_cancel);

        btnReturn.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("result_data", etInput.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        });

        btnCancel.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }
}