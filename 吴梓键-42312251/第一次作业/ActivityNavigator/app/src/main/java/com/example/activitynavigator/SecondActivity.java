package com.example.activitynavigator;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    private static final String CUSTOM_ACTION = "com.example.action.VIEW_THIRD_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        findViewById(R.id.btn_back_home).setOnClickListener(v -> finish());

        findViewById(R.id.btn_implicit_jump).setOnClickListener(v -> {
            Intent intent = new Intent(CUSTOM_ACTION);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            startActivity(intent);
        });
    }
}
