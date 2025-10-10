package com.example.activitynavigator;

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

    // 声明一个 EditText 变量，用于后面获取输入框
    private EditText etInput;

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

        // --- 以下是修改和新增的代码 ---

        // 1. 通过ID找到输入框
        etInput = findViewById(R.id.et_input);

        // 2. 通过ID找到“返回结果”按钮
        Button btnReturnResult = findViewById(R.id.btn_return_result);

        // 3. 给“返回结果”按钮设置点击事件
        btnReturnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建一个新的 Intent 用来存放数据
                Intent intent = new Intent();

                // 从 EditText 中获取用户输入的文本
                String inputText = etInput.getText().toString();

                // 将文本数据放入 Intent 中，键为 "result_data"
                intent.putExtra("result_data", inputText);

                // 设置返回结果码为 RESULT_OK，表示操作成功
                setResult(RESULT_OK, intent);

                // 结束当前 Activity，返回上一个界面 (MainActivity)
                finish();
            }
        });

        // 4. 通过ID找到“返回取消”按钮
        Button btnReturnCancel = findViewById(R.id.btn_return_cancel);

        // 5. 给“返回取消”按钮设置点击事件
        btnReturnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 设置返回结果码为 RESULT_CANCELED，表示操作被取消
                setResult(RESULT_CANCELED);

                // 结束当前 Activity，返回上一个界面 (MainActivity)
                finish();
            }
        });

        // --- 修改和新增代码结束 ---
    }
}