package com.fennuoyiman.homework001;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // 预留给第三部分的请求码
    public static final int REQUEST_CODE_THIRD_ACTIVITY = 101;
    private TextView tvResultDisplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnExplicitToSecond = findViewById(R.id.btn_explicit_to_second);

        // --- 第一部分：显式跳转逻辑 ---
        btnExplicitToSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 使用显式Intent (Explicit Intent)
                // Intent(Context packageContext, Class<?> cls)
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
        tvResultDisplay = findViewById(R.id.tv_result_display);
        Button btnStartForResult = findViewById(R.id.btn_start_for_result);

        // --- 第三部分：启动带结果的跳转逻辑 ---
        btnStartForResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
                // 使用 startActivityForResult 启动，并设置请求码
                startActivityForResult(intent, REQUEST_CODE_THIRD_ACTIVITY);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 1. 根据 requestCode 判断是否是我们启动 ThirdActivity 的请求
        if (requestCode == REQUEST_CODE_THIRD_ACTIVITY) {

            // 2. 根据 resultCode 判断返回结果是否成功
            if (resultCode == RESULT_OK) { // Activity.RESULT_OK = -1

                // 3. 从返回的 Intent (data) 中获取数据
                if (data != null) {
                    String resultData = data.getStringExtra("result_data");

                    // 4. 更新 TextView
                    tvResultDisplay.setText("ThirdActivity 返回的结果: " + resultData);
                    Toast.makeText(this, "成功接收结果: " + resultData, Toast.LENGTH_SHORT).show();
                }
            } else if (resultCode == RESULT_CANCELED) {
                // 如果用户按了返回键或调用了 finish() 而没有调用 setResult()
                tvResultDisplay.setText("跳转被取消，没有返回数据。");
            }
        }
    }
}