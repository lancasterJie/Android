package com.example.activitylifecyclelab;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            Log.d("Lifecycle", "SecondActivity - onCreate");
        }

        @Override
        protected void onStart() {
            super.onStart();
            Log.d("Lifecycle", "SecondActivity - onStart");
        }

        @Override
        protected void onResume() {
            super.onResume();
            Log.d("Lifecycle", "SecondActivity - onResume");
        }

        @Override
        protected void onPause() {
            super.onPause();
            Log.d("Lifecycle", "SecondActivity - onPause");
        }

        @Override
        protected void onStop() {
            super.onStop();
            Log.d("Lifecycle", "SecondActivity - onStop");
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            Log.d("Lifecycle", "SecondActivity - onDestroy");
        }

        @Override
        protected void onRestart() {
            super.onRestart();
            Log.d("Lifecycle", "SecondActivity - onRestart");
        }
}