package com.example.activitynavigator;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button btnToSecond, btnStartForResult;
    private TextView tvResult;
    private static final int REQUEST_CODE_THIRD = 101;
    private static final int REQUEST_CODE_SECOND = 201;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnToSecond = findViewById(R.id.btn_to_second);
        btnStartForResult = findViewById(R.id.btn_start_for_result);
        tvResult = findViewById(R.id.tv_result);

        btnToSecond.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivityForResult(intent,REQUEST_CODE_SECOND);
        });
        btnStartForResult.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
            startActivityForResult(intent, REQUEST_CODE_THIRD);
        });
        btnStartForResult.setOnLongClickListener(v -> {
            Toast.makeText(this, "长按启动了带返回结果的跳转！", Toast.LENGTH_SHORT).show();
            return true;
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_THIRD || requestCode == REQUEST_CODE_SECOND) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                String result = data.getStringExtra("result_data");
                tvResult.setText("结果显示：" + result);
            }
        }
    }
}
