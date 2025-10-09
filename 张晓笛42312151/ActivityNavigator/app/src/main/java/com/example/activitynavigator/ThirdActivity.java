package com.example.activitynavigator;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ThirdActivity extends AppCompatActivity {

    private EditText etInput;
    private Button btnReturnResult;
    private Button btnReturnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        initViews();
        setupClickListeners();
    }

    private void initViews() {
        etInput = findViewById(R.id.et_input);
        btnReturnResult = findViewById(R.id.btn_return_result);
        btnReturnCancel = findViewById(R.id.btn_return_cancel);
    }

    private void setupClickListeners() {
        // 返回结果
        btnReturnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnResult();
            }
        });

        // 返回取消（加分项）
        btnReturnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    private void returnResult() {
        String inputText = etInput.getText().toString().trim();

        Intent resultIntent = new Intent();
        resultIntent.putExtra("result_data", inputText);

        setResult(RESULT_OK, resultIntent);
        finish();
    }

    // 处理返回键
    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }
}