package com.example.activitynavigator;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {
    private Button btnBackToMain, btnImplicit;
    private static final String ACTION_VIEW_THIRD = "com.example.action.VIEW_THIRD_ACTIVITY";
    private static final int REQUEST_CODE_THIRD = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        btnBackToMain = findViewById(R.id.btn_back_to_main);
        btnImplicit = findViewById(R.id.btn_implicit);
        btnBackToMain.setOnClickListener(v -> finish());
        btnImplicit.setOnClickListener(v -> {
            Intent intent = new Intent(ACTION_VIEW_THIRD);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            startActivityForResult(intent, REQUEST_CODE_THIRD);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_THIRD) {
            setResult(resultCode, data);
            finish();
        }
    }
}