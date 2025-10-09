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

    private static final int REQUEST_CODE_THIRD_ACTIVITY = 101;

    private Button btnExplicitJump;
    private Button btnStartForResult;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupClickListeners();
    }

    private void initViews() {
        btnExplicitJump = findViewById(R.id.btn_explicit_jump);
        btnStartForResult = findViewById(R.id.btn_start_for_result);
        tvResult = findViewById(R.id.tv_result);
    }

    private void setupClickListeners() {
        // 显式跳转到 SecondActivity
        btnExplicitJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建显式Intent
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        // 带返回结果的跳转 - 点击事件
        btnStartForResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startThirdActivityForResult();
            }
        });

        // 带返回结果的跳转 - 长按事件（加分项）
        btnStartForResult.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(MainActivity.this, "长按启动了带返回结果的跳转！", Toast.LENGTH_SHORT).show();
                startThirdActivityForResult();
                return true;
            }
        });
    }

    private void startThirdActivityForResult() {
        Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
        startActivityForResult(intent, REQUEST_CODE_THIRD_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 检查请求码是否匹配
        if (requestCode == REQUEST_CODE_THIRD_ACTIVITY) {
            // 处理返回结果
            if (resultCode == RESULT_OK) {
                // 成功返回数据
                if (data != null) {
                    String resultData = data.getStringExtra("result_data");
                    if (resultData != null && !resultData.isEmpty()) {
                        tvResult.setText("返回结果: " + resultData);
                    } else {
                        tvResult.setText("返回结果: 空数据");
                    }
                }
            } else if (resultCode == RESULT_CANCELED) {
                // 用户取消操作
                tvResult.setText("操作已取消");
            }
        }
    }
}