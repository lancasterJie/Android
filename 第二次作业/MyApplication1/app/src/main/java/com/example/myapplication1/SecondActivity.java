package com.example.myapplication1;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {
    public static String TAG = "Lifecycle";
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        b1 = findViewById(R.id.back);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Log.d(TAG, "SecondActivity - onCreate");
    }


    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG, "SecondActivity - onStart");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG, "SecondActivity - onStop");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG, "SecondActivity - onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "SecondActivity - onRestart");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG, "SecondActivity - onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "SecondActivity - onDestroy");
    }

}