package com.example.experiment02;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

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
        printLog("onCreate");
        btn = findViewById(R.id.btnToSecondActivity);
        btn.setOnClickListener((v) ->{
            Log.d("wrz","Main to Second");
            startActivity(new Intent(MainActivity.this, SecondActivity.class));
        });
        btnCreateDialog = findViewById(R.id.btnCreateDialog);
        btnCreateDialog.setOnClickListener((v) ->{
            Log.d("wrz","Main to Dialog");
            startActivity(new Intent(MainActivity.this, DialogActivity.class));
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        printLog("onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        printLog("onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        printLog("onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        printLog("onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        printLog("onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        printLog("onDestroy");
    }

    private void printLog(String content) {
        Log.d("wrz", activityName + " - " + content);
    }
    public static final String activityName = "MainActivity";
    private Button btn, btnCreateDialog;
}