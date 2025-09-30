package com.example.activitynavigator;

import android.app.Activity;
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
    Button b1;  // 启动SecondActivity
    Button b2;  // 新增：启动带结果跳转的按钮
    TextView tvResult;  //显示返回结果的文本框
    // 请求码
    private static final int REQUEST_CODE_THIRD = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        b1 = findViewById(R.id.Button1);
        b1.setOnClickListener(new Mylistener());
        b2 = findViewById(R.id.Button2);
        tvResult = findViewById(R.id.tv_result);
        b2.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
            startActivityForResult(intent, REQUEST_CODE_THIRD);
        });
        b2.setOnLongClickListener(v -> {
            Toast.makeText(MainActivity.this, "长按启动了带返回结果的跳转！", Toast.LENGTH_SHORT).show();
            return true;
        });
    }
    class Mylistener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.Button1) {
                // 显式跳转启动SecondActivity
                Intent it = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(it);
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_THIRD) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("result_data");
                tvResult.setText("收到结果：" + result);
            }
            else if (resultCode == Activity.RESULT_CANCELED) {
                tvResult.setText("操作已取消");
            }
        }
    }
}