package com.example.activitynavigator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView tvResult;

    private static final int REQUEST_CODE_THIRD = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setupClickListeners();
    }

    private void initViews() {
        tvResult = findViewById(R.id.tv1);
    }

    private void setupClickListeners() {
        setupExplicitJump();
        setupResultJump();
    }

    private void setupExplicitJump() {
        Button btnToSecond = findViewById(R.id.second);
        btnToSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "正在跳转到 SecondActivity", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupResultJump() {
        Button btnStartForResult = findViewById(R.id.result);

        btnStartForResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
                intent.putExtra("request_data", "请在此输入返回的数据");
                startActivityForResult(intent, REQUEST_CODE_THIRD);
                tvResult.setText("等待 ThirdActivity 返回数据...");
                Toast.makeText(MainActivity.this, "已启动带返回结果的跳转", Toast.LENGTH_SHORT).show();
            }
        });

        btnStartForResult.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(MainActivity.this, "长按启动了带返回结果的跳转！", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 检查请求码是否匹配我们启动 ThirdActivity 时使用的代码
        if (requestCode == REQUEST_CODE_THIRD) {

            // 处理成功返回的情况
            if (resultCode == RESULT_OK) {
                // 检查data不为空，并且包含我们期望的数据
                if (data != null && data.hasExtra("result_data")) {
                    String result = data.getStringExtra("result_data");
                    // 检查返回数据是否为空
                    if (result != null && !result.trim().isEmpty()) {
                        tvResult.setText("yes!成功接收返回数据：\n" + result);
                        Toast.makeText(this, "成功接收到返回数据！", Toast.LENGTH_SHORT).show();
                    } else {
                        tvResult.setText("ohno!返回数据为空");
                        Toast.makeText(this, "返回的数据为空", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            else if (resultCode == RESULT_CANCELED) {
                tvResult.setText("ohno!用户取消了操作");
                Toast.makeText(this, "操作已取消", Toast.LENGTH_SHORT).show();
            }
            // 处理其他未知结果码
            else {
                tvResult.setText(" 404未知返回状态: " + resultCode);
            }
        }

    }

}
