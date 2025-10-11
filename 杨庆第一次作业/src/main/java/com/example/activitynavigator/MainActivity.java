package com.example.activitynavigator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // 请求码，用于标识带返回结果的跳转
    private static final int REQUEST_CODE_THIRD_ACTIVITY = 101;

    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.tv_result);

        // 显式跳转到SecondActivity
        Button btnExplicit = findViewById(R.id.btn_explicit);
        btnExplicit.setOnClickListener(v -> {
            // 创建显式Intent
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            // 启动SecondActivity
            startActivity(intent);
        });

        // 启动带结果的跳转
        Button btnStartForResult = findViewById(R.id.btn_start_for_result);
        btnStartForResult.setOnClickListener(v -> startThirdActivity());

        // 为按钮添加长按监听器（加分项）
        btnStartForResult.setOnLongClickListener(v -> {
            Toast.makeText(MainActivity.this, "长按启动了带返回结果的跳转！", Toast.LENGTH_SHORT).show();
            startThirdActivity();
            return true; // 表示事件已处理
        });
    }

    // 启动ThirdActivity用于获取返回结果
    private void startThirdActivity() {
        Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
        startActivityForResult(intent, REQUEST_CODE_THIRD_ACTIVITY);
    }

    // 处理从ThirdActivity返回的结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 检查请求码和结果码
        if (requestCode == REQUEST_CODE_THIRD_ACTIVITY) {
            if (resultCode == RESULT_OK && data != null) {
                // 获取返回的数据
                String result = data.getStringExtra("result_data");
                tvResult.setText("收到返回结果: " + result);
            } else if (resultCode == RESULT_CANCELED) {
                // 处理取消操作（加分项）
                tvResult.setText("操作已取消");
            }
        }
    }
}
