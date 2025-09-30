package com.example.activitynavigator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {

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
        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> finish());
        Button btnImplicit = findViewById(R.id.btn_implicit);
        btnImplicit.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction("com.example.action.VIEW_THIRD_ACTIVITY").setClassName(/* TODO: provide the application ID. For example: */ getPackageName(), "com.example.activitynavigator.ThirdActivity");
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            startActivity(intent);
        });
    }
}