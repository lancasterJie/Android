package com.example.activitynavigator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 101;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_explicit_jump).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        });

        tvResult = findViewById(R.id.tv_result);
        Button btnResultJump = findViewById(R.id.btn_result_jump);

        btnResultJump.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        });

        btnResultJump.setOnLongClickListener(v -> {
            Toast.makeText(this, "长按启动了带返回结果的跳转！", Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String result = data.getStringExtra("result_data");
                tvResult.setText("返回结果：" + result);
            } else if (resultCode == RESULT_CANCELED) {
                tvResult.setText("操作已取消");
            }
        }
    }
}