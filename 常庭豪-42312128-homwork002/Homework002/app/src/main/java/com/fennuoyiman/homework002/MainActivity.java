package com.fennuoyiman.homework002;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Lifecycle";
    private static final String NAME = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // 确保布局文件存在
        Log.d(TAG, NAME + " - onCreate");

        // 设置跳转到 SecondActivity 的按钮
        Button btnToSecond = findViewById(R.id.btn_to_second);
        btnToSecond.setOnClickListener(v -> startActivity(new Intent(this, SecondActivity.class)));

        // 设置跳转到 DialogActivity 的按钮
        Button btnToDialog = findViewById(R.id.btn_to_dialog);
        btnToDialog.setOnClickListener(v -> startActivity(new Intent(this, DialogActivity.class)));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, NAME + " - onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, NAME + " - onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, NAME + " - onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, NAME + " - onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, NAME + " - onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, NAME + " - onDestroy");
    }
}