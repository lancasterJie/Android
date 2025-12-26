package com.example.test;

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
    Button back, cancel;
    EditText et;
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
        et = findViewById(R.id.insert);//视图与逻辑代码链接
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {//监听器
            @Override
            public void onClick(View view) {
                Intent it = new Intent();//activity之间的通讯通过Intent实现
                it.putExtra("result", et.getText().toString());
                setResult(3,it);
                finish();
            }
        });
        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 设置结果为取消并结束Activity
                setResult(ThirdActivity.RESULT_CANCELED);
                finish();
            }
        });
    }
}
