package com.example.activitynavigator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_THIRD = 101;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化视图
        Button btnToSecond = findViewById(R.id.btn_to_second);
        Button btnStartForResult = findViewById(R.id.btn_start_for_result);
        tvResult = findViewById(R.id.tv_result);

        // 显式跳转到 SecondActivity
        btnToSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        // 带返回结果的跳转（点击）
        btnStartForResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
                startActivityForResult(intent, REQUEST_CODE_THIRD);
            }
        });

        // 带返回结果的跳转（长按）- 加分项
        btnStartForResult.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(MainActivity.this, "长按启动了带返回结果的跳转！", Toast.LENGTH_SHORT).show();
                return true; // 消费长按事件，不触发点击事件
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_THIRD) {
            if (resultCode == RESULT_OK) {
                // 处理成功返回的结果
                String result = data.getStringExtra("result_data");
                tvResult.setText("返回结果: " + result);
            } else if (resultCode == RESULT_CANCELED) {
                // 处理取消操作
                tvResult.setText("用户取消了操作");
            }
        }
    }
}