package com.example.activitynavigator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 第三个活动页面
 * 接收用户输入，并将结果返回给上一个活动
 */
public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置当前活动布局文件
        setContentView(R.layout.activity_third);

        // 从布局获取输入框组件（用户输入内容）
        EditText inputEt = findViewById(R.id.et_input);
        // 获取"确定"按钮（用于确认并返回结果）
        Button okBtn = findViewById(R.id.btn_return_ok);
        // 获取"取消"按钮（取消操作并返回）
        Button cancelBtn = findViewById(R.id.btn_return_cancel);

        /* "确定"按钮：返回输入的结果 */
        okBtn.setOnClickListener(v -> {
            // 创建Intent对象
            Intent data = new Intent();
            // 输入框文本内容存入Intent，键为"result_data"
            data.putExtra("result_data", inputEt.getText().toString());
            // 设置返回结果：结果码为RESULT_OK（操作成功），携带数据Intent
            setResult(Activity.RESULT_OK, data);
            // 销毁当前活动，返回上一个活动
            finish();
        });

        /* "取消"按钮：返回取消状态 */
        cancelBtn.setOnClickListener(v -> {
            // 设置返回结果：结果码为RESULT_CANCELED（操作取消），无数据
            setResult(Activity.RESULT_CANCELED);
            // 销毁当前活动，返回上一个活动
            finish();
        });
    }
}