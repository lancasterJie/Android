package com.example.activitylifecycle;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DialogActivity extends AppCompatActivity {

    Button close;
    public static final String TAG="lifecycle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 设置无标题栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_dialog);
        Log.d("Lifecycle", "DialogActivity - onCreate");

        // 设置窗口属性，使其看起来像对话框
        getWindow().setLayout(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        initViews();
        setupClickListeners();
    }

    private void initViews() {
        close = findViewById(R.id.CloseDialog);
    }

    private void setupClickListeners() {
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 关闭对话框
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "DialogActivity - onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "DialogActivity - onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "DialogActivity - onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "DialogActivity - onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "DialogActivity - onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "DialogActivity - onRestart");
    }
}