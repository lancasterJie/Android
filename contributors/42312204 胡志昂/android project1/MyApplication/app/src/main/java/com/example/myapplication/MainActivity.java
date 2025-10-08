package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 101;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.tv_result);

        // 显式跳转到SecondActivity
        Button btnToSecond = findViewById(R.id.btn_to_second);
        btnToSecond.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        });

        // 带结果的跳转到ThirdActivity
        Button btnStartForResult = findViewById(R.id.btn_start_for_result);
        btnStartForResult.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        });

        // 长按监听器（加分项）
        btnStartForResult.setOnLongClickListener(v -> {
            Toast.makeText(MainActivity.this, "长按启动了带返回结果的跳转！", Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK && data != null) {
                String result = data.getStringExtra("result_data");
                tvResult.setText("返回结果: " + result);
            } else if (resultCode == RESULT_CANCELED) {
                tvResult.setText("用户取消了操作");
            }
        }
    }
}