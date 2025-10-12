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

public class SecondActivity extends AppCompatActivity {
    private static final String TAG = "Lifecycle";
    private static final String NAME = "SecondActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second); // 确保布局文件存在
        Log.d(TAG, NAME + " - onCreate");
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