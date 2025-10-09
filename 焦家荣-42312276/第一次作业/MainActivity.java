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

    private static final int REQUEST_CODE = 101;
    private Button btnToSecond, btnStartForResult;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupClickListeners();
    }

    private void initViews() {
        btnToSecond = findViewById(R.id.btn_to_second);
        btnStartForResult = findViewById(R.id.btn_start_for_result);
        tvResult = findViewById(R.id.tv_result);
    }

    private void setupClickListeners() {
        // 显式跳转到SecondActivity
        btnToSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        // 带结果的跳转 - 点击事件
        btnStartForResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        // 带结果的跳转 - 长按事件
        btnStartForResult.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(MainActivity.this, "长按启动了带返回结果的跳转！", Toast.LENGTH_SHORT).show();
                return true; // 返回true表示消费了长按事件
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // 处理成功返回的结果
                if (data != null) {
                    String resultData = data.getStringExtra("result_data");
                    tvResult.setText("返回结果: " + resultData);
                }
            } else if (resultCode == RESULT_CANCELED) {
                // 处理取消操作
                tvResult.setText("用户取消了操作");
            }
        }
    }
}