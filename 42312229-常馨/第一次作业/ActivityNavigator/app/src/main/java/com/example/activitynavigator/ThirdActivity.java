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

        // 初始化视图
        etInput = findViewById(R.id.et_input);
        Button btnReturnResult = findViewById(R.id.btn_return_result);
        Button btnReturnCancel = findViewById(R.id.btn_return_cancel);

        // 返回结果按钮点击事件
        btnReturnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取输入文本
                String inputText = etInput.getText().toString().trim();

                // 创建返回数据的Intent
                Intent resultIntent = new Intent();
                resultIntent.putExtra("result_data", inputText);

                // 设置结果并结束Activity
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        // 返回取消按钮点击事件（加分项）
        btnReturnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 设置取消结果并结束Activity
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}