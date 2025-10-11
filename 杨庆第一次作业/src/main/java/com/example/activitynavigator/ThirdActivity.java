package com.example.activitynavigator;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ThirdActivity extends AppCompatActivity {

    private EditText etInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        etInput = findViewById(R.id.et_input);

        // 返回结果按钮
        Button btnReturnResult = findViewById(R.id.btn_return_result);
        btnReturnResult.setOnClickListener(v -> {
            // 获取输入的文本
            String inputText = etInput.getText().toString();

            // 创建返回数据的Intent
            Intent intent = new Intent();
            intent.putExtra("result_data", inputText);

            // 设置返回结果
            setResult(RESULT_OK, intent);

            // 结束当前Activity
            finish();
        });

        // 返回取消按钮（加分项）
        Button btnReturnCancel = findViewById(R.id.btn_return_cancel);
        btnReturnCancel.setOnClickListener(v -> {
            // 设置取消结果
            setResult(RESULT_CANCELED);
            // 结束当前Activity
            finish();
        });
    }
}
