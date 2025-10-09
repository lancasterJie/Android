package com.example.application1;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.application1.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static final int REQUEST_CODE_THIRD_ACTIVITY = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 跳转到 SecondActivity 的按钮点击事件
        binding.btnToSecond.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        });

        // 启动带结果跳转的按钮点击事件
        binding.btnStartForResult.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
            startActivityForResult(intent, REQUEST_CODE_THIRD_ACTIVITY);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_THIRD_ACTIVITY) {
            if (resultCode == RESULT_OK && data != null) {
                String resultData = data.getStringExtra("result_data");
                if (resultData != null && !resultData.isEmpty()) {
                    binding.tvResult.setText("返回结果: " + resultData);
                } else {
                    binding.tvResult.setText("返回结果: 无数据");
                }
            } else if (resultCode == RESULT_CANCELED) {
                binding.tvResult.setText("用户取消了操作");
            }
        }
    }
}