package com.fennuoyiman.homework001;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ThirdActivity extends AppCompatActivity {

    private EditText etInputResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        etInputResult = findViewById(R.id.et_input_result);
        Button btnReturnResult = findViewById(R.id.btn_return_result);

        // --- 第三部分：设置返回结果逻辑 ---
        btnReturnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. 获取用户输入
                String resultText = etInputResult.getText().toString();

                // 2. 创建一个 Intent，用于携带返回的数据
                Intent returnIntent = new Intent();
                // 将数据放入 Intent 的 Extra 中
                returnIntent.putExtra("result_data", resultText);

                // 3. 设置返回结果码和携带数据的 Intent
                // Activity.RESULT_OK 表示成功
                setResult(Activity.RESULT_OK, returnIntent);

                // 4. 结束当前 Activity，返回到调用它的 Activity (MainActivity)
                finish();
            }
        });

        // 可选：如果用户直接按返回键，确保返回 RESULT_CANCELED
        // 默认情况下，按返回键会返回 RESULT_CANCELED，所以这步通常是可选的，除非你有特殊逻辑
    }
}