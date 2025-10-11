package com.example.activitylifecyclelab;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_second).setOnClickListener(v -> {
            startActivity(new Intent(this, SecondActivity.class));
        });

        findViewById(R.id.btn_dialog).setOnClickListener(v -> {
            startActivity(new Intent(this, DialogActivity.class));
        });
        Log.d("Lifecycle", "MainActivity - onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Lifecycle", "MainActivity - onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Lifecycle", "MainActivity - onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Lifecycle", "MainActivity - onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Lifecycle", "MainActivity - onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Lifecycle", "MainActivity - onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Lifecycle", "MainActivity - onRestart");
    }
}