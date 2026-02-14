package com.example.activitynavigator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 第二个活动页面
 * 包含返回主页和隐式跳转到第三个活动的功能
 */
public class SecondActivity extends AppCompatActivity {

    // 定义隐式跳转ThirdActivity的动作常量
    // 包名+动作名
    public static final String ACTION_VIEW_THIRD =
            "com.example.action.VIEW_THIRD_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置当前活动布局文件
        setContentView(R.layout.activity_second);

        /* 返回到主页功能 */
        Button backBtn = findViewById(R.id.btn_back);
        // 设置点击事件监听器
        backBtn.setOnClickListener(v -> {
            // 调用finish()销毁当前活动，返回上一个活动（主页）
            finish();
        });

        /* 隐式跳转ThirdActivity功能 */
        Button implicitBtn = findViewById(R.id.btn_implicit);
        //设置点击事件监听器
        implicitBtn.setOnClickListener(v -> {
            // 创建意图对象，指定要执行的动作（跳转到ThirdActivity）
            Intent intent = new Intent(ACTION_VIEW_THIRD);
            // 添加默认类别
            intent.addCategory(Intent.CATEGORY_DEFAULT);

            // 是否有能处理该意图的活动
            // resolveActivity()方法会查找匹配的活动，存在返回非null
            if (intent.resolveActivity(getPackageManager()) != null) {
                // 若存在匹配的活动，则启动该活动
                startActivity(intent);
            }
        });
    }
}