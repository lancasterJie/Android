package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    private static final String TAG = "Lifecycle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Log.d(TAG, "SecondActivity - onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "SecondActivity - onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "SecondActivity - onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "SecondActivity - onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "SecondActivity - onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "SecondActivity - onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "SecondActivity - onRestart");
    }
}