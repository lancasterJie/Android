package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ThirdActivity extends AppCompatActivity {

    private EditText etInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        etInput = findViewById(R.id.et_input);

        // 返回结果
        Button btnReturnResult = findViewById(R.id.btn_return_result);
        btnReturnResult.setOnClickListener(v -> {
            String result = etInput.getText().toString();
            Intent intent = new Intent();
            intent.putExtra("result_data", result);
            setResult(RESULT_OK, intent);
            finish();
        });

        // 返回取消（加分项）
        Button btnCancel = findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }
}