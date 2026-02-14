package com.example.lifecycle;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * 对话框式 Activity
 * 使用 Theme.AppCompat.Dialog 主题，以对话框形式显示。
 * 启动 Dialog Activity 时，主 Activity 仅进入 onPause，不会 onStop。
 */
public class DialogActivity extends AppCompatActivity {

    private static final String TAG = "Jiayi"; // Log 标签

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "DialogActivity - onCreate");
        setContentView(R.layout.activity_dialog);

        // 处理系统栏内边距
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.dialog_layout), (v, insets) -> {
            int systemBars = WindowInsetsCompat.Type.systemBars();
            v.setPadding(
                    insets.getInsets(systemBars).left,
                    insets.getInsets(systemBars).top,
                    insets.getInsets(systemBars).right,
                    insets.getInsets(systemBars).bottom
            );
            return insets;
        });

        // 关闭按钮：关闭对话框 Activity
        findViewById(R.id.btn_close_dialog).setOnClickListener(v -> finish());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "DialogActivity - onStart"); // 即将可见
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "DialogActivity - onResume"); // 开始交互
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "DialogActivity - onPause"); // 暂停
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "DialogActivity - onStop"); // 不可见
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "DialogActivity - onDestroy"); // 即将销毁
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "DialogActivity - onRestart"); // 重启
    }
}