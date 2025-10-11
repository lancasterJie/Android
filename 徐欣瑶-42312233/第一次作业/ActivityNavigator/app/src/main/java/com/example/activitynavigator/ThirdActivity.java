package com.example.activitynavigator;

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
        Button btnReturnResult = findViewById(R.id.btn_return_result);
        Button btnReturnCancel = findViewById(R.id.btn_return_cancel);

        // 返回结果给MainActivity
        btnReturnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText = etInput.getText().toString().trim();

                Intent resultIntent = new Intent();
                resultIntent.putExtra("result_data", inputText.isEmpty() ? "默认文本" : inputText);

                setResult(RESULT_OK, resultIntent);
                finish(); // 结束当前Activity，返回MainActivity
            }
        });

        // 返回取消 - 加分项
        btnReturnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish(); // 结束当前Activity，返回MainActivity
            }
        });
    }
}