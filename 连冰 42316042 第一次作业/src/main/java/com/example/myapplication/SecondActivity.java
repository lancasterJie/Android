package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        EdgeToEdge.enable(this);
        // 通过id找到“返回到主页”按钮
        Button back = findViewById(R.id.back);

        // 为按钮设置点击事件监听器
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 调用finish()方法，结束当前Activity，返回上一个Activity（MainActivity）
                finish();
            }
        });
        Button implicitBtn = findViewById(R.id.button11);
        implicitBtn.setOnClickListener(v -> {
            // 创建隐式Intent
            Intent implicitIntent = new Intent();
            // 设置自定义Action（必须与AndroidManifest中配置一致）
            implicitIntent.setAction("com.example.action.VIEW_THIRD_ACTIVITY");
            // 设置Category（默认类别，隐式跳转必须包含）
            implicitIntent.addCategory(Intent.CATEGORY_DEFAULT);

            // 启动符合条件的Activity（此处会匹配到ThirdActivity）
            startActivity(implicitIntent);
        });
    }
}