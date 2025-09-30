package com.example.activitynavigator;

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

    EditText etInput;  // 输入框：用于输入返回的文本
    Button b3;  // “返回结果”按钮
    Button b4;  // “返回取消”按钮
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_third);
        // 处理窗口内边距
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // 绑定控件
        etInput = findViewById(R.id.et_input);
        b3 = findViewById(R.id.Button3);
        b4 = findViewById(R.id.Button4);
        b3.setOnClickListener(v -> {
            String inputText = etInput.getText().toString().trim();
            Intent result = new Intent();
            result.putExtra("result_data", inputText);
            setResult(Activity.RESULT_OK, result);
            finish();
        });
        b4.setOnClickListener(v -> {
            setResult(Activity.RESULT_CANCELED);
            finish();
        });
    }
}