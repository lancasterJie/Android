package com.snowerwwww.application2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DialogActivity extends AppCompatActivity {

    //activity在用户可见时调用
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Lifecycle","DialogActivity - onStart");
    }

    //和用户交互时调用
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("LifeCycle","DialogActivity - onResume");
    }

    //其他activity获得焦点时调用
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("LifeCycle","DialogActivity - onPause");
    }

    //activity不再可见时调用
    @Override
    protected void onStop() {
        super.onStop();
        Log.d("LifeCycle","DialogActivity - onStop");
    }

    //停止的activity重新启动时调用
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("LifeCycle","DialogActivity - onRestart");
    }

    //被销毁前调用
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("LifeCycle","DialogActivity - onDestroy");
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        Log.d("DialogActivityLifecycle", "onCreate");

        // 设置关闭按钮的点击事件
        Button btnClose = findViewById(R.id.button3);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 关闭这个Dialog Activity
            }
        });
    }
}