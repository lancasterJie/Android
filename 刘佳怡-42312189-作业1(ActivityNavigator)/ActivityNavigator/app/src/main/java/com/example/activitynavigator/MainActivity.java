package com.example.activitynavigator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_CODE_WITH_RESULT = 101; // 请求码，标识跳转来源
    private TextView resultTv; // 返回结果的文本

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 获取界面控件
        Button gotoSecondBtn = findViewById(R.id.btn_goto_second);
        Button gotoForResultBtn = findViewById(R.id.btn_goto_for_result);
        resultTv = findViewById(R.id.tv_result);

        /* 1. 显式跳转 SecondActivity */
        gotoSecondBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent); // 启动目标Activity
        });

        /* 3. 带返回结果的跳转 ThirdActivity */
        gotoForResultBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
            startActivityForResult(intent, REQ_CODE_WITH_RESULT); // 等待返回结果
        });

        /* 长按按钮 Toast */
        gotoForResultBtn.setOnLongClickListener(v -> {
            Toast.makeText(this, "长按启动了带返回结果的跳转！", Toast.LENGTH_SHORT).show();
            return false;        // 不拦截事件，继续执行单击
        });
    }

    /* 接收 ThirdActivity 返回的数据 */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
      //返回结果的请求码是否是发起的“带结果跳转”这一次操作
        if (requestCode == REQ_CODE_WITH_RESULT) {
          //如果目标页面返回的结果是“成功”，并且带回数据，则显示
            if (resultCode == Activity.RESULT_OK && data != null) {
                String txt = data.getStringExtra("result_data");
                resultTv.setText("收到结果：" + txt); // 显示返回内容
            } else if (resultCode == Activity.RESULT_CANCELED) {
                resultTv.setText("取消操作"); // 处理取消情况
            }
        }
    }
}