package com.example.second;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    public static final  String TAG="Lifecycle";

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"MainActivity - onDestroy");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"MainActivity - onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"MainActivity - onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"MainActivity - onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"MainActivity - onStart");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Log.d(TAG,"MainActivity - onCreate");
    }
}