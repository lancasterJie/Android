package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // 返回主页
        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> finish());

        // 隐式跳转
        Button btnImplicit = findViewById(R.id.btn_implicit);
        btnImplicit.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction("com.example.action.VIEW_THIRD_ACTIVITY");
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            startActivity(intent);
        });
    }
}